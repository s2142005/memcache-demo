package com.networking.memcache.demo.app.presentation;

import android.content.Context;
import android.util.DisplayMetrics;

public class PXUtils {

    public static int dpToPx(Context context, int dp) {
        return Math.round(dp * (context.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context context, int px) {
        return Math.round(
                px / (context.getResources().getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}