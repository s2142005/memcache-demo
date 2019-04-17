package com.networking.memcache.demo.data.repository.unsplash;

import com.networking.memcache.demo.data.response.unsplash.FetchUnsplashUsersResponse;

import java.io.IOException;

public interface UnsplashEndpointInterface {

    FetchUnsplashUsersResponse fetchUsers(String url) throws IOException;

    byte[] loadImageAsBytes(String url);
}
