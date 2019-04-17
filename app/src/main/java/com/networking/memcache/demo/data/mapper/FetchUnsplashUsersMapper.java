package com.networking.memcache.demo.data.mapper;

import com.networking.memcache.demo.data.response.unsplash.FetchUnsplashUsersResponse;
import com.networking.memcache.demo.data.response.unsplash.PhotosItem;
import com.networking.memcache.demo.data.response.unsplash.ResultsItem;
import com.networking.memcache.demo.domain.model.Photo;
import com.networking.memcache.demo.domain.model.PhotoUrl;
import com.networking.memcache.demo.domain.model.ProfileImage;
import com.networking.memcache.demo.domain.model.UnsplashUser;

import java.util.ArrayList;
import java.util.List;

public class FetchUnsplashUsersMapper {

    public static List<UnsplashUser> map(FetchUnsplashUsersResponse response) {
        final List<UnsplashUser> userList = new ArrayList<>();
        for (ResultsItem item : response.getResults()) {
            UnsplashUser user = new UnsplashUser();
            user.setFirstName(item.getFirstName());
            user.setLastName(item.getLastName());
            user.setName(item.getName());
            user.setUsername(item.getUsername());
            user.setId(item.getId());

            ProfileImage profileImage = new ProfileImage();
            profileImage.setLarge(item.getProfileImage().getLarge());
            profileImage.setMedium(item.getProfileImage().getMedium());
            profileImage.setSmall(item.getProfileImage().getSmall());
            user.setProfileImage(profileImage);

            user.setTotalLikes(item.getTotalLikes());
            final List<Photo> photos = new ArrayList<>();
            for (PhotosItem p : item.getPhotos()) {
                final Photo photo = new Photo();
                photo.setId(p.getId());

                final PhotoUrl url = new PhotoUrl();
                url.setFull(p.getUrls().getFull());
                url.setRaw(p.getUrls().getRaw());
                url.setRegular(p.getUrls().getRegular());
                url.setSmall(p.getUrls().getSmall());
                url.setThumb(p.getUrls().getThumb());

                photo.setUrls(url);

                photos.add(photo);
            }

            user.setPhotos(photos);

            userList.add(user);
        }

        return userList;
    }
}
