package org.superbiz.moviefun.blobstore;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.apache.tika.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/blobstore")
public class BlobStoreController {


    private BlobStore blobStore;

    public BlobStoreController(BlobStore blobStore) {
        this.blobStore = blobStore;
    }

    @PostMapping
    public void put(@RequestBody Blob blob) throws IOException {
        blobStore.put(blob);
    }


    @GetMapping("/{albumCover}")
    public Blob get(@PathVariable String albumCover) throws IOException {
        return blobStore.get(albumCover).get();
    }
}
