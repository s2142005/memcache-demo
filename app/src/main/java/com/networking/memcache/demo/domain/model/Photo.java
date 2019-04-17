package com.networking.memcache.demo.domain.model;

public class Photo {

    private PhotoUrl urls;
    private String id;

    public PhotoUrl getUrls() {
        return urls;
    }

    public void setUrls(PhotoUrl urls) {
        this.urls = urls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
