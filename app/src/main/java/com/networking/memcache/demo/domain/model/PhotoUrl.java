package com.networking.memcache.demo.domain.model;

import com.networking.memcache.demo.data.response.unsplash.PhotosItem;
import com.networking.memcache.demo.data.response.unsplash.Urls;

public class PhotoUrl {

    private String small;
    private String thumb;
    private String raw;
    private String regular;
    private String full;

    public PhotoUrl(PhotosItem item) {
        this.full = item.getUrls().getFull();
        this.raw = item.getUrls().getRaw();
        this.regular = item.getUrls().getRegular();
        this.small = item.getUrls().getSmall();
        this.thumb = item.getUrls().getThumb();
    }

    public PhotoUrl(Urls urls) {
        this.full = urls.getFull();
        this.raw = urls.getRaw();
        this.regular = urls.getRegular();
        this.small = urls.getSmall();
        this.thumb = urls.getThumb();
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }
}
