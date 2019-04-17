package com.networking.memcache.demo.data.response.unsplash;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Links {

    @JsonProperty("followers")
    private String followers;

    @JsonProperty("portfolio")
    private String portfolio;

    @JsonProperty("following")
    private String following;

    @JsonProperty("self")
    private String self;

    @JsonProperty("html")
    private String html;

    @JsonProperty("photos")
    private String photos;

    @JsonProperty("likes")
    private String likes;

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}