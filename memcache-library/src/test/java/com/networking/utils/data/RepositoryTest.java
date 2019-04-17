package com.networking.utils.data;

import com.networking.utils.cache.MemoryCache;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

@RunWith(RobolectricTestRunner.class)
public class RepositoryTest {

    private static final String MOCK_IMG_URL = "https://mock.info/mock-img.jpg";
    private static final String FILE_URL = "https://drive.google.com/open?id=1lqKjCwCbfGWmFoaZvndBkqSDjwI5eEDA";
    private static final String MOCK_NAME = "MOCK_OBJECT_NAME";
    private static final int MOCK_ID = 1000;
    private static final MockItem MOCK_ITEM = new MockItem();

    /**
     * Test Method Naming Convention:
     * "Should_ExpectedBehavior_When_StateUnderTest"
     */

    private Repository repo;

    @Before
    public void setUp() throws Exception {
        MemoryCache cache = new MemoryCache(3, 2, 5, TimeUnit.SECONDS);
        String key = UUID.nameUUIDFromBytes(MOCK_ITEM.url.getBytes()).toString();
        cache.put(key, MOCK_ITEM);
        repo = new Repository(cache);
    }

    @After
    public void tearDown() throws Exception {
        repo.cleanUp();
        repo = null;
    }

    @Test
    public void Should_ReturnItemFromCache_When_TheItemExistsInCache() {
        Observable.just(repo.loadJSON(MOCK_IMG_URL))
                .subscribeOn(Schedulers.newThread())
                .subscribe(jsonObject -> {
                    Assert.assertNotNull(jsonObject);
                    Assert.assertEquals(MOCK_ITEM, jsonObject);
                    Assert.assertTrue(jsonObject instanceof MockItem);
                    MockItem mockItem = (MockItem) jsonObject;
                    Assert.assertEquals(MOCK_ID, mockItem.id);
                    Assert.assertEquals(MOCK_NAME, mockItem.name);
                });
    }

    @Test
    public void Should_ReturnObjectFetchedFromNetwork_When_TheObjectNotExistInCache() throws Exception {

        final String key = UUID.nameUUIDFromBytes(FILE_URL.getBytes()).toString();
        Assert.assertFalse(repo.isExisted(key));
        Assert.assertNull(repo.getFromCache(key));
        Assert.assertEquals(1, repo.cacheSize());
        final String jsonFileUrl = "https://api.unsplash.com/search/users?page=1&query=john&client_id=1d14d58a4235fc32835dcb8f83cc7c5ffff87960814932d2d243768dd8c78a0d";
        JSONObject output = repo.loadJSON(jsonFileUrl);
        Assert.assertNotEquals(MOCK_ITEM, output);
        Assert.assertEquals(2, repo.cacheSize());
    }

    static class MockItem extends JSONObject {

        final String url = MOCK_IMG_URL;
        final int id = MOCK_ID;
        final String name = MOCK_NAME;
    }
}