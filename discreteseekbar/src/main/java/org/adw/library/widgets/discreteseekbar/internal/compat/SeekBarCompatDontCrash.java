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

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.internal.drawable.MarkerDrawable;

/**
 * Wrapper compatibility class to call some API-Specific methods
 * And offer alternate procedures when possible
 *
 * @hide
 */
@TargetApi(21)
class SeekBarCompatDontCrash {
    public static void setOutlineProvider(View marker, final MarkerDrawable markerDrawable) {
        marker.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setConvexPath(markerDrawable.getPath());
            }
        });
    }

    public static Drawable getRipple(ColorStateList colorStateList) {
        return new RippleDrawable(colorStateList, null, null);
    }

    public static void setBackground(View view, Drawable background) {
        view.setBackground(background);
    }

    public static void setTextDirection(TextView number, int textDirection) {
        number.setTextDirection(textDirection);
    }

    public static boolean isInScrollingContainer(ViewParent p) {
        while (p != null && p instanceof ViewGroup) {
            if (((ViewGroup) p).shouldDelayChildPressedState()) {
                return true;
            }
            p = p.getParent();
        }
        return false;
    }

    public static boolean isHardwareAccelerated(View view) {
        return view.isHardwareAccelerated();
    }
}
