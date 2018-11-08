package org.superbiz.moviefun.albums

import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.superbiz.moviefun.CsvUtils
import org.superbiz.moviefun.blobstore.BlobStore

import java.io.IOException


import com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType
import com.fasterxml.jackson.dataformat.csv.CsvSchema.builder
import java.util.function.Consumer
import java.util.function.Predicate

@Service
class AlbumsUpdater(private val blobStore: BlobStore, private val albumsRepository: AlbumsRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val objectReader: ObjectReader

    init {

        val schema = builder()
                .addColumn("artist")
                .addColumn("title")
                .addColumn("year", ColumnType.NUMBER)
                .addColumn("rating", ColumnType.NUMBER)
                .build()

        objectReader = CsvMapper().readerFor(Album::class.java).with(schema)
    }

    @Throws(IOException::class)
    fun update() {
        val maybeBlob = blobStore.get("albums.csv")

        if (!maybeBlob.isPresent) {
            logger.info("No albums.csv found when running AlbumsUpdater!")
            return
        }

        val albumsToHave = CsvUtils.readFromCsv<Album>(objectReader, maybeBlob.get().inputStream)
        val albumsWeHave = albumsRepository.albums

        createNewAlbums(albumsToHave, albumsWeHave)
        deleteOldAlbums(albumsToHave, albumsWeHave)
        updateExistingAlbums(albumsToHave, albumsWeHave)
    }


    private fun createNewAlbums(albumsToHave: List<Album>, albumsWeHave: List<Album>) {
        val albumsToCreate = albumsToHave
                .filter { album -> albumsWeHave.stream().noneMatch(Predicate<Album> { album.isEquivalent(it) }) }

        albumsToCreate.forEach(Consumer<Album> { albumsRepository.addAlbum(it) })
    }

    private fun deleteOldAlbums(albumsToHave: List<Album>, albumsWeHave: List<Album>) {
        val albumsToDelete = albumsWeHave
                .stream()
                .filter { album -> albumsToHave.stream().noneMatch(Predicate<Album> { album.isEquivalent(it) }) }

        albumsToDelete.forEach(Consumer<Album> { albumsRepository.deleteAlbum(it) })
    }

    private fun updateExistingAlbums(albumsToHave: List<Album>, albumsWeHave: List<Album>) {
        val albumsToUpdate = albumsToHave
                .stream()
                .map { album -> addIdToAlbumIfExists(albumsWeHave, album) }
                .filter(Predicate<Album> { it.hasId() })

        albumsToUpdate.forEach(Consumer<Album> { albumsRepository.updateAlbum(it) })
    }

    private fun addIdToAlbumIfExists(existingAlbums: List<Album>, album: Album): Album {
        val maybeExisting = existingAlbums.stream().filter(Predicate<Album> { album.isEquivalent(it) }).findFirst()
        maybeExisting.ifPresent { existing -> album.id = existing.id }
        return album
    }
}
