package com.networking.utils.endpoints.impls;

import com.networking.utils.endpoints.RetrofitFactory;
import com.networking.utils.endpoints.interfaces.APIServices;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class APIClient {

    private static final String BASE_URL = "https://images.unsplash.com/";

    public APIClient() {
    }

    public Call<ResponseBody> download(
            final String url,
            OkHttpClient httpClient) {

        return RetrofitFactory
                .build(BASE_URL, httpClient)
                .create(APIServices.class)
                .downloadFile(url);
    }
}
