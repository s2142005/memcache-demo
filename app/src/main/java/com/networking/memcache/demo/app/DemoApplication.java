package com.networking.memcache.demo.app;

import android.app.Application;

import com.networking.utils.data.Repository;

import java.util.concurrent.TimeUnit;

public class DemoApplication extends Application {

    public static final String TAG = "DemoApp";
    public static DemoApplication instance;
    private Repository dataRepo;

    public static DemoApplication getInstance() {
        return instance;
    }

    public Repository getDataRepo() {
        return dataRepo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        buildDataRepo();
    }

    private void buildDataRepo() {
        dataRepo = Repository.build(5 * 60, 6 * 60, 100, TimeUnit.SECONDS);
    }
}
