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

package org.adw.library.widgets.discreteseekbar.internal.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

/**
 * A drawable that changes it's Paint color depending on the Drawable State
 * <p>
 * Subclasses should implement {@link #doDraw(Canvas, Paint)}
 * </p>
 *
 * @hide
 */
public abstract class StateDrawable extends Drawable {
    private ColorStateList mTintStateList;
    private final Paint mPaint;
    private int mCurrentColor;
    private int mAlpha = 255;

    public StateDrawable(@NonNull ColorStateList tintStateList) {
        super();
        setColorStateList(tintStateList);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public boolean isStateful() {
        return (mTintStateList.isStateful()) || super.isStateful();
    }

    @Override
    public boolean setState(int[] stateSet) {
        boolean handled = super.setState(stateSet);
        handled = updateTint(stateSet) || handled;
        return handled;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    private boolean updateTint(int[] state) {
        final int color = mTintStateList.getColorForState(state, mCurrentColor);
        if (color != mCurrentColor) {
            mCurrentColor = color;
            //We've changed states
            invalidateSelf();
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        mPaint.setColor(mCurrentColor);
        int alpha = modulateAlpha(Color.alpha(mCurrentColor));
        mPaint.setAlpha(alpha);
        doDraw(canvas, mPaint);
    }

    public void setColorStateList(@NonNull ColorStateList tintStateList) {
        mTintStateList = tintStateList;
        mCurrentColor = tintStateList.getDefaultColor();
    }

    /**
     * Subclasses should implement this method to do the actual drawing
     *
     * @param canvas The current {@link Canvas} to draw into
     * @param paint  The {@link Paint} preconfigurred with the current
     *               {@link ColorStateList} color
     */
    abstract void doDraw(Canvas canvas, Paint paint);

    @Override
    public void setAlpha(int alpha) {
        mAlpha = alpha;
        invalidateSelf();
    }

    int modulateAlpha(int alpha) {
        int scale = mAlpha + (mAlpha >> 7);
        return alpha * scale >> 8;
    }

    @Override
    public int getAlpha() {
        return mAlpha;
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

}
