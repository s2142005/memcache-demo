package com.networking.memcache.demo.data.mapper;

import com.networking.memcache.demo.data.response.unsplash.FetchUnsplashUsersResponse;
import com.networking.memcache.demo.domain.model.UnsplashUser;

import java.util.List;

public class FetchUnsplashUsersMapper {

    public static List<UnsplashUser> map(FetchUnsplashUsersResponse response) {
        return MapperUtil.buildUserList(response.getResults());
    }
}
