package com.networking.utils.endpoints;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpClientFactory {

    private static final int CONNECTION_TIMEOUT_IN_SECONDS = 60;
    private static final int READ_TIMEOUT_IN_SECONDS = 60;
    private static final int WRITE_TIMEOUT_IN_SECONDS = 60;

    public static OkHttpClient build() {

        final HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(logInterceptor);

        builder.connectTimeout(CONNECTION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);

        return builder.build();
    }
}
