package com.networking.utils.download.impls;

import android.util.Log;

import com.networking.utils.download.interfaces.IDownload;
import com.networking.utils.endpoints.OkHttpClientFactory;
import com.networking.utils.endpoints.impls.APIClient;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class Download_JSON implements IDownload {

    public static final String TAG = "Download_JSON";
    private Call<ResponseBody> call;

    @Override
    public JSONObject fetch(String url) {
        call = new APIClient().download(url, OkHttpClientFactory.build());
        if (call == null) {
            return null;
        }

        try {
            return doNetWorkCall();
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }

        return null;
    }

    @Override
    public void cancel() {
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
    }

    @Override
    public boolean isCanceled() {
        return call == null ? false : call.isCanceled();
    }

    private JSONObject doNetWorkCall() throws Exception {
        Response<ResponseBody> result = call.execute();
        if (result.isSuccessful() && result.body() != null) {
            return new JSONObject(result.body().string());
        }

        return null;
    }
}
