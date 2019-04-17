package com.networking.memcache.demo.app.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.networking.memcache.demo.R;
import com.networking.memcache.demo.data.repository.unsplash.UnsplashEndpointImpl;

import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GalleryAdapter extends PagerAdapter {

    @DrawableRes
    int holder;
    @Nullable
    private List<String> pics;
    @Nullable
    private List<Integer> picsRes;

    public GalleryAdapter(
            @Nullable List<String> pics,
            @Nullable List<Integer> picsRes,
            @DrawableRes int holder) {
        this.pics = pics;
        this.picsRes = picsRes;
        this.holder = holder;
    }

    @Override
    public int getCount() {
        if (picsRes != null) {
            return picsRes.size();
        } else if (pics != null) {
            return pics.size();
        }
        return 0;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_big_pic, container, false);
        final ImageView targetImage = view.findViewById(R.id.imv_photo);
        UnsplashEndpointImpl endpoint = new UnsplashEndpointImpl();
        Observable.just(endpoint.loadImageAsBytes(pics.get(position)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bytes -> {
                    Glide.with(container.getContext()).load(bytes).into(targetImage);
                });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
