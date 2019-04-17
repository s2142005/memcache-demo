package com.networking.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.networking.utils.cache.MemoryCache;
import com.networking.utils.download.impls.Download_IMG;
import com.networking.utils.endpoints.OkHttpClientFactory;
import com.networking.utils.endpoints.impls.APIClient;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

@RunWith(RobolectricTestRunner.class)
public class MemoryCacheTest {

    private static final String PROFILE_IMAGE_NOT_EXIST = "https://images.unsplash"
            + ".com/profile-1464495186405-68089dcd96c4";

    private static final String PROFILE_IMAGE_SMALL = "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128&s=622a88097cf6661f84cd8942d851d9a2";
    private static final String PROFILE_IMAGE_MEDIUM = "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.5\\&q=80\\&fm=jpg\\&crop=faces\\&fit=crop\\&h=64\\&w=64\\&s=ef631d113179b3137f911a05fea56d23";
    private static final String PROFILE_IMAGE_LARGE = "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5\\&q=80\\&fm=jpg\\&crop=faces\\&fit=crop\\&h=128\\&w=128\\&s=622a88097cf6661f84cd8942d851d9a2";

    private static final String[] PROFILE_IMAGE_LIST = new String[] {PROFILE_IMAGE_SMALL, PROFILE_IMAGE_MEDIUM,
            PROFILE_IMAGE_LARGE};

    private static final int MAX_ITEMS_IN_CACHE = 2;
    private static final long TIMETOLIVE_IN_MILLIS = 60 * 1000;
    private static final long CLEANUP_INTERVAL_IN_MILLIS = 500;

    private final List<String> keys = new ArrayList<>();
    private MemoryCache MEMORY_CACHE;
    private CompositeDisposable disposables;
    private Download_IMG imgDownloader;

    @Before
    public void setup() {
        MEMORY_CACHE = new MemoryCache(TIMETOLIVE_IN_MILLIS,
                CLEANUP_INTERVAL_IN_MILLIS, MAX_ITEMS_IN_CACHE, TimeUnit.MILLISECONDS);
        imgDownloader = new Download_IMG();
        disposables = new CompositeDisposable();
    }

    @After
    public void tearDown() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
            disposables.clear();
        }
    }

    @Test
    public void Should_IncreaseCacheSizeByOne_When_PutNewItemToCache() {

        Download_IMG downloadImg = new Download_IMG();
        byte[] output = downloadImg.fetch(PROFILE_IMAGE_LARGE);
        String key = UUID.nameUUIDFromBytes(PROFILE_IMAGE_LARGE.getBytes()).toString();
        int sizeBefore = MEMORY_CACHE.size();
        MEMORY_CACHE.put(key, output);
        int sizeAfter = MEMORY_CACHE.size();
        Assert.assertEquals((sizeBefore + 1), sizeAfter);
    }

    @Test
    public void Should_ReturnItemAssociatedWithTheKey_When_ValidKeyIsGiven() throws InterruptedException {
        final Download_IMG downloadImg = new Download_IMG();
        byte[] output = downloadImg.fetch(PROFILE_IMAGE_SMALL);
        String key = UUID.nameUUIDFromBytes(PROFILE_IMAGE_SMALL.getBytes()).toString();
        MEMORY_CACHE.put(key, output);
        Thread.sleep(500);
        byte[] smallBitmap = (byte[]) MEMORY_CACHE.get(key);
        Assert.assertEquals(output, smallBitmap);
    }

    @Test
    public void Should_ReturnNull_When_GivenKeyWhichHasBeenRemovedFromCache() throws InterruptedException {
        byte[] output = imgDownloader.fetch(PROFILE_IMAGE_SMALL);
        String key = UUID.randomUUID().toString();
        MEMORY_CACHE.put(key, output);
        Thread.sleep(500);
        MEMORY_CACHE.remove(key);
        Thread.sleep(500);
        Assert.assertNull(MEMORY_CACHE.get(key));
    }

    @Test
    public void Should_ReturnOne_When_LastCacheSizeIsOne() {

        byte[] output = imgDownloader.fetch(PROFILE_IMAGE_MEDIUM);
        String key = UUID.randomUUID().toString();
        MEMORY_CACHE.put(key, output);
        int size = MEMORY_CACHE.size();
        Assert.assertEquals(1, size);
    }

    @Test
    public void Should_HaveCacheSizeEqualsMaxItems_When_PutMoreItemsThanMaxSize() throws
            IOException {

        Call<ResponseBody> loadLargeImg = new APIClient().download(PROFILE_IMAGE_LARGE,
                OkHttpClientFactory.build());
        Call<ResponseBody> loadMediumImg = new APIClient().download(PROFILE_IMAGE_MEDIUM,
                OkHttpClientFactory.build());
        Call<ResponseBody> loadSmallImg = new APIClient().download(PROFILE_IMAGE_SMALL,
                OkHttpClientFactory.build());

        Disposable disposable = Observable.just(
                loadLargeImg.execute(),
                loadMediumImg.execute(),
                loadSmallImg.execute()
        ).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap((Function<Response<ResponseBody>, Observable<Bitmap>>) bodyResponse -> {
                    byte[] data = bodyResponse.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    return Observable.just(bitmap);
                })
                .subscribe(bitmap -> {
                    String key = UUID.randomUUID().toString();
                    keys.add(key);
                    if (MEMORY_CACHE.size() == MAX_ITEMS_IN_CACHE) {
                        // attempt to access the 2nd item on the list
                        // this will make the cache known that it should
                        // remove other item (less frequent accessed items) than the 2nd item when it
                        // has reached its maxSize.
                        MEMORY_CACHE.get(keys.get(1));

                        // This will force the 1st item cleared out from cache
                        MEMORY_CACHE.put(key, bitmap);

                        Assert.assertEquals(MAX_ITEMS_IN_CACHE, MEMORY_CACHE.size());
                    } else {
                        MEMORY_CACHE.put(key, bitmap);
                    }

                }, System.out::println);

        disposables.add(disposable);
    }

    @Test
    public void Should_HaveCacheSizeEqualsZero_When_CleanupFuncIsCalled() {

        byte[] output = imgDownloader.fetch(PROFILE_IMAGE_MEDIUM);
        String key = UUID.randomUUID().toString();
        MEMORY_CACHE.put(key, output);
        Assert.assertEquals(1, MEMORY_CACHE.size());

        MEMORY_CACHE.cleanup();
        Assert.assertEquals(0, MEMORY_CACHE.size());
    }
}
