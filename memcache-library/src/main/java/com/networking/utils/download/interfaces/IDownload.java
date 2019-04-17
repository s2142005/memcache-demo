package com.networking.utils.download.interfaces;

public interface IDownload<T> {

    T fetch(String url);

    void cancel();

    boolean isCanceled();
}
