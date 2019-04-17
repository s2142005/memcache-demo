package com.networking.utils.download.impls;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


@RunWith(RobolectricTestRunner.class)
public class Download_IMGTest {

    /**
     * Test Method Naming Convention:
     * "Should_ExpectedBehavior_When_StateUnderTest"
     */

    private static final String IMAGE_URL = "https://drive.google.com/open?id=1Kjai7bxY3QGH7lpVAY6TAPrzbek0RDVeoQ";
    private static final String NONE_EXIST_IMG_URL = "https://random.invalid.url";
    private Download_IMG downloadImg;

    @Before
    public void setUp() throws Exception {
        downloadImg = new Download_IMG();
    }

    @After
    public void tearDown() throws Exception {
        downloadImg = null;
    }

    @Test
    public void Should_ReturnValidObject_When_ValidUrlGive() throws Exception {
        byte[] output = downloadImg.fetch(IMAGE_URL);
        Assert.assertNotNull(output);
    }

    @Test
    public void Should_ReturnNull_When_InValidUrlGiven() throws Exception {
        byte[] output = downloadImg.fetch(NONE_EXIST_IMG_URL);
        Assert.assertNull(output);
    }

    @Test
    public void Should_ReturnTrue_When_isCanceledIsCalledAfterCancelTheRunningJob() throws Exception {
        Observable.just(downloadImg.fetch(IMAGE_URL))
                .subscribeOn(Schedulers.newThread())
                .delay(3, TimeUnit.SECONDS)
                .subscribe();

        downloadImg.cancel();
        Assert.assertTrue(downloadImg.isCanceled());
    }
}