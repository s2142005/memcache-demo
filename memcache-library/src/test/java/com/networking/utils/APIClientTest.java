package com.networking.utils;

import com.networking.utils.download.impls.Download_IMG;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@RunWith(RobolectricTestRunner.class)
public class APIClientTest {

    final String PROFILE_IMAGE_NOT_EXIST = "https://images.unsplash.com/profile-1464495186405-68089dcd96c4";
    final String PROFILE_IMAGE_SMALL = "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128&s=622a88097cf6661f84cd8942d851d9a2";
    final String PROFILE_IMAGE_MEDIUM = "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.5\\&q=80\\&fm=jpg\\&crop=faces\\&fit=crop\\&h=64\\&w=64\\&s=ef631d113179b3137f911a05fea56d23";
    final String PROFILE_IMAGE_LARGE = "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5\\&q=80\\&fm=jpg\\&crop=faces\\&fit=crop\\&h=128\\&w=128\\&s=622a88097cf6661f84cd8942d851d9a2";
    Download_IMG imgDownload_SUT;
    String[] PROFILE_IMAGE = new String[] {PROFILE_IMAGE_SMALL, PROFILE_IMAGE_MEDIUM, PROFILE_IMAGE_LARGE};

    @Before
    public void setup() {
        imgDownload_SUT = new Download_IMG();
    }

    /**
     * Test Method Naming Convention:
     * "Should_ExpectedBehavior_When_StateUnderTest"
     */

    @Test
    public void Should_ReturnNoneNull_When_ValidUrlIsGiven() {
        Observable.just(imgDownload_SUT.fetch(PROFILE_IMAGE_LARGE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Assert::assertNotNull);
    }

    @Test
    public void Should_ReturnNull_When_InvalidUrlIsGiven() {
        Assert.assertNull(imgDownload_SUT.fetch(PROFILE_IMAGE_NOT_EXIST));
    }

    @Test
    public void Should_HaveStatusAsCanceled_When_CanelFuncIsCalled() {
        Download_IMG imgDownloader = new Download_IMG();

        Observable.just(imgDownloader.fetch(PROFILE_IMAGE_LARGE))
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        imgDownloader.cancel();
        Assert.assertTrue(imgDownloader.isCanceled());
    }
}