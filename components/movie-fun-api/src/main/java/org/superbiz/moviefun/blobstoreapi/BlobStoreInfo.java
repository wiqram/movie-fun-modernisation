package org.superbiz.moviefun.blobstoreapi;

import java.io.InputStream;
import java.util.Objects;

public class BlobStoreInfo {
    public final String name;
    public final InputStream inputStream;
    public final String contentType;

    public BlobStoreInfo(String name, InputStream inputStream, String contentType) {
        this.name = name;
        this.inputStream = inputStream;
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlobStoreInfo that = (BlobStoreInfo) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(inputStream, that.inputStream) &&
                Objects.equals(contentType, that.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, inputStream, contentType);
    }

    public String getName() {
        return name;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getContentType() {
        return contentType;
    }
}
