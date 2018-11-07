package org.superbiz.moviefun.blobstoreapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;
import org.superbiz.moviefun.albumsapi.AlbumInfo;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Repository
public class BlobStoreClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    String blobstoreUrl;
    RestOperations restOperations;

    public BlobStoreClient(String blobstoreUrl, RestOperations restOperations) {
        this.blobstoreUrl = blobstoreUrl;
        this.restOperations = restOperations;
    }

    public BlobStoreInfo get(@RequestAttribute String albumCover) {
        ParameterizedTypeReference<BlobStoreInfo> blobStoreType = new ParameterizedTypeReference<BlobStoreInfo>() {
        };
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(blobstoreUrl)
                .queryParam("albumCover", albumCover);

        return restOperations.exchange(builder.toUriString(), GET, null, blobStoreType).getBody();
    }

    public void put(@RequestBody BlobStoreInfo blobStoreInfo) {

        restOperations.put(blobstoreUrl, blobStoreInfo);
    }
}
