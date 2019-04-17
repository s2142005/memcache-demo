package com.networking.utils.data;

import android.util.Log;

import com.networking.utils.cache.MemoryCache;
import com.networking.utils.download.impls.Download_IMG;
import com.networking.utils.download.impls.Download_JSON;
import com.networking.utils.endpoints.Constants;

import org.json.JSONObject;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;

public class Repository {

    private static final String TAG = "Repository";
    private static Repository repo;
    private MemoryCache cache;

    public Repository() {
        this.cache = new MemoryCache(Constants.DEFAULT_TIMETOLIVE_IN_MILLIS,
                Constants.DEFAULT_CLEANUP_INTERVAL_IN_MILLIS, Constants.DEFAULT_MAX_ITEMS, TimeUnit.MILLISECONDS);
    }

    public Repository(MemoryCache cache) {
        this.cache = cache;
    }

    public static Repository build(int timeToLive, int evictInterval, int maxItem, TimeUnit timeUnit) {
        final MemoryCache cache = new MemoryCache(timeToLive, evictInterval, maxItem, timeUnit);
        repo = new Repository(cache);
        return repo;
    }

    public JSONObject loadJSON(String url) {
        final String key = UUID.nameUUIDFromBytes(url.getBytes()).toString();
        if (cache.get(key) != null) {
            return (JSONObject) cache.get(key);
        }

        Download_JSON jsonLoader = new Download_JSON();
        JSONObject jsonObject = jsonLoader.fetch(url);
        cache.put(key, jsonObject);

        return jsonObject;
    }

    public byte[] loadImageAsBytes(String url) {

        final String key = UUID.nameUUIDFromBytes(url.getBytes()).toString();
        if (cache.get(key) != null) {
            Log.d(TAG, "Found image already in cache -> return bitmap from cache!");
            return (byte[]) cache.get(key);
        }

        Log.d(TAG, "image not existing in cache -> load it from network source!");
        Download_IMG imgLoader = new Download_IMG();
        byte[] data = (byte[]) imgLoader.fetch(url);

        Log.d(TAG, "put loaded bitmap in cache for later reuse!");
        cache.put(key, data);

        return data;
    }


    public boolean isExisted(String key) {
        if (cache.get(key) != null) {
            return true;
        }
        return false;
    }

    public int cacheSize() {
        return cache.size();
    }

    @Nullable
    public Object getFromCache(String key) {
        return cache.get(key);
    }


    public void cleanUp() {
        cache.cleanup();
    }
}
