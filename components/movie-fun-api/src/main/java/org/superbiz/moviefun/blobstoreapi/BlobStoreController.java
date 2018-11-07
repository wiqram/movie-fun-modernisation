/*
package org.superbiz.moviefun.blobstoreapi;

import org.apache.tika.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.superbiz.moviefun.albumsapi.AlbumsClient;
import org.superbiz.moviefun.blobstore.BlobStore;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/blobstore")
public class BlobStoreController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BlobStoreClient blobStoreClient;

    public BlobStoreController(BlobStoreClient blobStoreClient) {
        this.blobStoreClient = blobStoreClient;
    }

    @GetMapping("/{albumId}")
    public HttpEntity<byte[]> getCover(@PathVariable long albumId) throws IOException, URISyntaxException {
        BlobStoreInfo maybeCoverBlob = blobStoreClient.get(getCoverBlobName(albumId));
        BlobStoreInfo coverBlob = (maybeCoverBlob != null) ? maybeCoverBlob : buildDefaultCoverBlob();
        byte[] imageBytes = IOUtils.toByteArray(coverBlob.inputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(coverBlob.contentType));
        headers.setContentLength(imageBytes.length);

        return new HttpEntity<>(imageBytes, headers);
    }
}
*/
