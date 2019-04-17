package com.networking.memcache.demo.data.response.unsplash;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResultsItem {

    @JsonProperty("total_photos")
    private int totalPhotos;

    @JsonProperty("accepted_tos")
    private boolean acceptedTos;

    @JsonProperty("followed_by_user")
    private boolean followedByUser;

    @JsonProperty("twitter_username")
    private Object twitterUsername;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("bio")
    private Object bio;

    @JsonProperty("total_likes")
    private int totalLikes;

    @JsonProperty("photos")
    private List<PhotosItem> photos;

    @JsonProperty("portfolio_url")
    private Object portfolioUrl;

    @JsonProperty("profile_image")
    private ProfileImage profileImage;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("name")
    private String name;

    @JsonProperty("location")
    private Object location;

    @JsonProperty("links")
    private Links links;

    @JsonProperty("total_collections")
    private int totalCollections;

    @JsonProperty("id")
    private String id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("instagram_username")
    private Object instagramUsername;

    @JsonProperty("username")
    private String username;

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public boolean isAcceptedTos() {
        return acceptedTos;
    }

    public void setAcceptedTos(boolean acceptedTos) {
        this.acceptedTos = acceptedTos;
    }

    public boolean isFollowedByUser() {
        return followedByUser;
    }

    public void setFollowedByUser(boolean followedByUser) {
        this.followedByUser = followedByUser;
    }

    public Object getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(Object twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Object getBio() {
        return bio;
    }

    public void setBio(Object bio) {
        this.bio = bio;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public List<PhotosItem> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosItem> photos) {
        this.photos = photos;
    }

    public Object getPortfolioUrl() {
        return portfolioUrl;
    }

    public void setPortfolioUrl(Object portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public int getTotalCollections() {
        return totalCollections;
    }

    public void setTotalCollections(int totalCollections) {
        this.totalCollections = totalCollections;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Object getInstagramUsername() {
        return instagramUsername;
    }

    public void setInstagramUsername(Object instagramUsername) {
        this.instagramUsername = instagramUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}