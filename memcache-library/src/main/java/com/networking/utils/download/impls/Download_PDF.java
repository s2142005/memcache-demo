package com.networking.utils.download.impls;

import com.networking.utils.download.interfaces.IDownload;

import org.json.JSONObject;

// TODO: to complete the implementation
public class Download_PDF implements IDownload {

    @Override
    public JSONObject fetch(String url) {
        return null;
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isCanceled() {
        return false;
    }
}
