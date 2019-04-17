package com.networking.utils.cache;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MemoryCache<K, T> {

    private static final long DEFAULT_CLEANUP_INTERVAL_IN_SECONDS = 1;
    private static final long DEFAULT_TIMETOLIVE_IN_SECONDS = 60;
    private static final int DEFAULT_MAX_ITEMS = 100;

    private final long timeToLiveInSeconds;
    private final long cleanupIntervalInSeconds;
    private final int maxItems;
    private final Cache<K, T> CACHE_SPACE;

    public MemoryCache() {
        this.timeToLiveInSeconds = DEFAULT_TIMETOLIVE_IN_SECONDS;
        this.cleanupIntervalInSeconds = DEFAULT_CLEANUP_INTERVAL_IN_SECONDS;
        this.maxItems = DEFAULT_MAX_ITEMS;

        CACHE_SPACE = Caffeine.newBuilder()
                .maximumSize(maxItems)
                .expireAfterWrite(timeToLiveInSeconds, TimeUnit.SECONDS)
                .refreshAfterWrite(cleanupIntervalInSeconds, TimeUnit.SECONDS)
                .build();
    }

    public MemoryCache(long timeToLive, long cleanupInterval, int maxItems, TimeUnit unit) {
        this.timeToLiveInSeconds = timeToLive;
        this.cleanupIntervalInSeconds = cleanupInterval;
        this.maxItems = maxItems;

        CACHE_SPACE = Caffeine.newBuilder()
                .maximumSize(maxItems)
                .expireAfterWrite(timeToLive, unit)
                .build();
    }

    public void put(K key, T value) {
        CACHE_SPACE.asMap().put(key, value);
    }

    public T get(K key) {
        return CACHE_SPACE.asMap().get(key);
    }

    public void remove(K key) {
        CACHE_SPACE.asMap().remove(key);
    }

    public int size() {
        return CACHE_SPACE.asMap().size();
    }

    public void cleanup() {
        CACHE_SPACE.asMap().clear();
    }

    public Set<K> getKeySet() {
        return CACHE_SPACE.asMap().keySet();
    }
}
