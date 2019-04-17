package com.networking.memcache.demo.data.repository.unsplash;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networking.memcache.demo.app.DemoApplication;
import com.networking.memcache.demo.data.response.unsplash.FetchUnsplashUsersResponse;

import org.json.JSONObject;

import java.io.IOException;

public class UnsplashEndpointImpl implements UnsplashEndpointInterface {

    public FetchUnsplashUsersResponse fetchUsers(String url) throws IOException {
        JSONObject jsonObject = DemoApplication.getInstance().getDataRepo().loadJSON(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        return mapper.readValue(jsonObject.toString(), FetchUnsplashUsersResponse.class);
    }

    @Override
    public byte[] loadImageAsBytes(String url) {
        return DemoApplication.getInstance().getDataRepo().loadImageAsBytes(url);
    }
}
