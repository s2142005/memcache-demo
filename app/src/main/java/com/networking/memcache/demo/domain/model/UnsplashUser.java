package com.networking.memcache.demo.domain.model;

import com.networking.memcache.demo.data.response.unsplash.ResultsItem;

import java.util.List;

public class UnsplashUser {

    private String lastName;
    private int totalLikes;
    private List<Photo> photos;
    private ProfileImage profileImage;
    private String name;
    private String id;
    private String firstName;
    private String username;

    public UnsplashUser(ResultsItem item) {
        this.firstName = item.getFirstName();
        this.lastName = item.getLastName();
        this.name = item.getName();
        this.username = item.getUsername();
        this.id = item.getId();
        this.totalLikes = item.getTotalLikes();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}