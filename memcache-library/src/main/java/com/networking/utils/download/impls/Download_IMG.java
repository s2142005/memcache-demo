package com.networking.utils.download.impls;

import android.util.Log;

import com.networking.utils.download.interfaces.IDownload;
import com.networking.utils.endpoints.OkHttpClientFactory;
import com.networking.utils.endpoints.impls.APIClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class Download_IMG implements IDownload {

    private static final String TAG = "Download_IMG";
    private Call<ResponseBody> call;

    public Download_IMG() {

    }

    @Override
    public byte[] fetch(String url) {
        return fetchAsBytes(url);
    }

    @Override
    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }

    @Override
    public boolean isCanceled() {
        return (call == null ? false : call.isCanceled());
    }

    private byte[] fetchAsBytes(String url) {
        call = new APIClient().download(url, OkHttpClientFactory.build());
        if (call == null) {
            return null;
        }

        try {
            Response<ResponseBody> result = call.execute();
            if (result.isSuccessful() && result.body() != null) {
                return result.body().bytes();
            }
        } catch (IOException e) {
            Log.e(TAG, "" + e);
        }

        return null;
    }
}
