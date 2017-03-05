package com.github.vipulasri.timeline.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Vipul on 28/12/16.
 */

public class VectorDrawableUtils {

    public static Drawable getDrawable(Context context, int drawableResId) {
        Drawable drawable;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            drawable = context.getResources().getDrawable(drawableResId, context.getTheme());
        } else {
            drawable = VectorDrawableCompat.create(context.getResources(), drawableResId, context.getTheme());
        }

        return drawable;
    }

    public static Drawable getDrawable(Context context, int drawableResId, int colorFilter) {
        Drawable drawable = getDrawable(context, drawableResId);
        drawable.setColorFilter(ContextCompat.getColor(context, colorFilter), PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    public static Bitmap getBitmap(Context context, int drawableId) {
        Drawable drawable = getDrawable(context, drawableId);

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
