package com.networking.memcache.demo.data.mapper;

import com.networking.memcache.demo.data.response.unsplash.PhotosItem;
import com.networking.memcache.demo.data.response.unsplash.ResultsItem;
import com.networking.memcache.demo.domain.model.Photo;
import com.networking.memcache.demo.domain.model.PhotoUrl;
import com.networking.memcache.demo.domain.model.ProfileImage;
import com.networking.memcache.demo.domain.model.UnsplashUser;

import java.util.ArrayList;
import java.util.List;

public class MapperUtil {

    public static List<UnsplashUser> buildUserList(List<ResultsItem> results) {

        final List<UnsplashUser> userList = new ArrayList<>(results.size());
        for (ResultsItem item : results) {
            UnsplashUser user = new UnsplashUser(item);

            ProfileImage profileImage = new ProfileImage(
                    item.getProfileImage().getSmall(),
                    item.getProfileImage().getMedium(),
                    item.getProfileImage().getLarge());
            user.setProfileImage(profileImage);

            final List<Photo> photos = buildPhotoList(item.getPhotos());
            user.setPhotos(photos);
            userList.add(user);
        }

        return userList;
    }

    private static List<Photo> buildPhotoList(List<PhotosItem> photos) {
        final List<Photo> results = new ArrayList<>(photos.size());
        for (PhotosItem p : photos) {
            final Photo photo = new Photo();
            photo.setId(p.getId());
            final PhotoUrl url = new PhotoUrl(p.getUrls());
            photo.setUrls(url);
            results.add(photo);
        }
        return results;
    }
}
