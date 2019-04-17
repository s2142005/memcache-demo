package com.networking.memcache.demo.data.response.unsplash;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhotosItem {

    @JsonProperty("urls")
    private Urls urls;

    @JsonProperty("id")
    private String id;

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}