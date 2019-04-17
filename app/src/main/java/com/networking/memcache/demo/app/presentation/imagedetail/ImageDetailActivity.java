package com.networking.memcache.demo.app.presentation.imagedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.networking.memcache.demo.R;
import com.networking.memcache.demo.app.presentation.BaseActivity;
import com.networking.memcache.demo.app.presentation.PXUtils;
import com.networking.memcache.demo.app.presentation.adapter.GalleryAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.viewpager.widget.ViewPager;

public class ImageDetailActivity extends BaseActivity {

    private static final String PICS = "PICS";
    private static final String PICSRES = "PICSRES";
    private static final String NEEDCLOSE = "NEEDCLOSE";
    private static final String POSITION = "POSITION";
    private static final String NEED_POINT = "NEED_POINT";
    private static final String HOLDER = "HOLDER";
    private static final String ALLOW_ZOOM = "ALLOW_ZOOM";

    private ViewPager picsViewPager;
    private LinearLayout point;
    private int size;

    public static Intent startIntent(
            Context context,
            List<String> pics,
            int position,
            boolean needPoint,
            @DrawableRes int holder,
            boolean allowZoom) {
        Intent intent = new Intent(context, ImageDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (pics instanceof ArrayList) {
            intent.putStringArrayListExtra(PICS, (ArrayList<String>) pics);
        } else {
            ArrayList<String> picList = new ArrayList<>();
            for (String s : pics) {
                picList.add(s);
                intent.putStringArrayListExtra(PICS, picList);
            }
        }
        intent.putExtra(POSITION, position);
        intent.putExtra(NEED_POINT, needPoint);
        intent.putExtra(HOLDER, holder);
        intent.putExtra(ALLOW_ZOOM, allowZoom);
        return intent;
    }

    @Override
    public int contentViewResId() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        List<String> pics = intent.getStringArrayListExtra(PICS);
        List<Integer> picsRes = intent.getIntegerArrayListExtra(PICSRES);
        int holder = getIntent().getIntExtra(HOLDER, R.drawable.img_holder);
        final boolean needClose = getIntent().getBooleanExtra(NEEDCLOSE, false);

        GalleryAdapter galleryAdapter = new GalleryAdapter(pics, picsRes, holder);
        size = pics.size();

        point = findViewById(R.id.point);
        picsViewPager = findViewById(R.id.picsViewPager);
        picsViewPager.setAdapter(galleryAdapter);

        picsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (needClose && position == size - 1) {
                    setResult(RESULT_OK);
                    finish();
                } else if (getIntent().getBooleanExtra(NEED_POINT, false)) {
                    drawPointChange(size, position);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        int position = getIntent().getIntExtra(POSITION, 0);
        picsViewPager.setCurrentItem(position);
        if (getIntent().getBooleanExtra(NEED_POINT, false)) {
            drawPointInit(size - (getIntent().getBooleanExtra(NEEDCLOSE, false) ? 1 : 0), position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void drawPointChange(int sum, int now) {
        for (int i = 0; i < sum; i++) {
            if (i == now) {
                ((ImageView) point.getChildAt(i)).setImageResource(R.drawable.point_now);
            } else {
                ((ImageView) point.getChildAt(i)).setImageResource(R.drawable.point_not_now);
            }
        }
    }

    private void drawPointInit(int sum, int now) {
        point.removeAllViews();
        for (int i = 0; i < sum; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setPadding(PXUtils.dpToPx(this, 3), PXUtils.dpToPx(this, 6), PXUtils.dpToPx(this, 3),
                    PXUtils.dpToPx(this, 6));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i == now) {
                imageView.setImageResource(R.drawable.point_now);
                point.addView(imageView, layoutParams);
            } else {
                imageView.setImageResource(R.drawable.point_not_now);
                point.addView(imageView, layoutParams);
            }
            final int finalI = i;
            imageView.setOnClickListener(v -> picsViewPager.setCurrentItem(finalI));
        }
    }
}
