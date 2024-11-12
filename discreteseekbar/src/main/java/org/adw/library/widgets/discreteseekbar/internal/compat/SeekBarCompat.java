/*
 * Copyright (c) Gustavo Claramunt (AnderWeb) 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.adw.library.widgets.discreteseekbar.internal.compat;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;

import org.adw.library.widgets.discreteseekbar.internal.drawable.MarkerDrawable;

/**
 * Wrapper compatibility class to call some API-Specific methods
 * And offer alternate procedures when possible
 *
 * @hide
 */
public class SeekBarCompat {

    /**
     * Sets the custom Outline provider on API>=21.
     * Does nothing on API<21
     *
     * @param view
     * @param markerDrawable
     */
    public static void setOutlineProvider(View view, final MarkerDrawable markerDrawable) {
        SeekBarCompatDontCrash.setOutlineProvider(view, markerDrawable);
    }

    /**
     * Our DiscreteSeekBar implementation uses a circular drawable on API < 21
     * because we don't set it as Background, but draw it ourselves
     *
     * @param colorStateList
     * @return
     */
    public static Drawable getRipple(ColorStateList colorStateList) {
        return SeekBarCompatDontCrash.getRipple(colorStateList);
    }

    /**
     * Sets the color of the seekbar ripple
     * @param drawable
     * @param colorStateList The ColorStateList the track ripple will be changed to
     */
    public static void setRippleColor(@NonNull Drawable drawable, ColorStateList colorStateList) {
        ((RippleDrawable) drawable).setColor(colorStateList);
    }

    /**
     * As our DiscreteSeekBar implementation uses a circular drawable on API < 21
     * we want to use the same method to set its bounds as the Ripple's hotspot bounds.
     *
     * @param drawable
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setHotspotBounds(Drawable drawable, int left, int top, int right, int bottom) {
        //We don't want the full size rect, Lollipop ripple would be too big
        int size = (right - left) / 8;
        DrawableCompat.setHotspotBounds(drawable, left + size, top + size, right - size, bottom - size);
    }

    /**
     * android.support.v4.view.ViewCompat SHOULD include this once and for all!!
     * But it doesn't...
     *
     * @param view
     * @param background
     */
    @SuppressWarnings("deprecation")
    public static void setBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            SeekBarCompatDontCrash.setBackground(view, background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    /**
     * Sets the TextView text direction attribute when possible
     *
     * @param textView
     * @param textDirection
     * @see TextView#setTextDirection(int)
     */
    public static void setTextDirection(TextView textView, int textDirection) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            SeekBarCompatDontCrash.setTextDirection(textView, textDirection);
        }
    }

    public static boolean isInScrollingContainer(ViewParent p) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return SeekBarCompatDontCrash.isInScrollingContainer(p);
        }
        return false;
    }

    public static boolean isHardwareAccelerated(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return SeekBarCompatDontCrash.isHardwareAccelerated(view);
        }
        return false;
    }
}
