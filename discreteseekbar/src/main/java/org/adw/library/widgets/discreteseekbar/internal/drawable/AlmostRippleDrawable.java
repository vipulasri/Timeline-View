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
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;

public class AlmostRippleDrawable extends StateDrawable implements Animatable {
    private static final long FRAME_DURATION = 1000 / 60;
    private static final int ANIMATION_DURATION = 250;

    private static final float INACTIVE_SCALE = 0f;
    private static final float ACTIVE_SCALE = 1f;
    private float mCurrentScale = INACTIVE_SCALE;
    private Interpolator mInterpolator;
    private long mStartTime;
    private boolean mReverse = false;
    private boolean mRunning = false;
    private int mDuration = ANIMATION_DURATION;
    private float mAnimationInitialValue;
    //We don't use colors just with our drawable state because of animations
    private int mPressedColor;
    private int mFocusedColor;
    private int mDisabledColor;
    private int mRippleColor;
    private int mRippleBgColor;

    public AlmostRippleDrawable(@NonNull ColorStateList tintStateList) {
        super(tintStateList);
        mInterpolator = new AccelerateDecelerateInterpolator();
        setColor(tintStateList);
    }

    public void setColor(@NonNull ColorStateList tintStateList) {
        int defaultColor = tintStateList.getDefaultColor();
        mFocusedColor = tintStateList.getColorForState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, defaultColor);
        mPressedColor = tintStateList.getColorForState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, defaultColor);
        mDisabledColor = tintStateList.getColorForState(new int[]{-android.R.attr.state_enabled}, defaultColor);

        //The ripple should be partially transparent
        mFocusedColor = getModulatedAlphaColor(130, mFocusedColor);
        mPressedColor = getModulatedAlphaColor(130, mPressedColor);
        mDisabledColor = getModulatedAlphaColor(130, mDisabledColor);
    }

    private static int getModulatedAlphaColor(int alphaValue, int originalColor) {
        int alpha = Color.alpha(originalColor);
        int scale = alphaValue + (alphaValue >> 7);
        alpha = alpha * scale >> 8;
        return Color.argb(alpha, Color.red(originalColor), Color.green(originalColor), Color.blue(originalColor));
    }

    @Override
    public void doDraw(Canvas canvas, Paint paint) {
        Rect bounds = getBounds();
        int size = Math.min(bounds.width(), bounds.height());
        float scale = mCurrentScale;
        int rippleColor = mRippleColor;
        int bgColor = mRippleBgColor;
        float radius = (size / 2);
        float radiusAnimated = radius * scale;
        if (scale > INACTIVE_SCALE) {
            if (bgColor != 0) {
                paint.setColor(bgColor);
                paint.setAlpha(decreasedAlpha(Color.alpha(bgColor)));
                canvas.drawCircle(bounds.centerX(), bounds.centerY(), radius, paint);
            }
            if (rippleColor != 0) {
                paint.setColor(rippleColor);
                paint.setAlpha(modulateAlpha(Color.alpha(rippleColor)));
                canvas.drawCircle(bounds.centerX(), bounds.centerY(), radiusAnimated, paint);
            }
        }
    }

    private int decreasedAlpha(int alpha) {
        int scale = 100 + (100 >> 7);
        return alpha * scale >> 8;
    }

    @Override
    public boolean setState(int[] stateSet) {
        int[] oldState = getState();
        boolean oldPressed = false;
        for (int i : oldState) {
            if (i == android.R.attr.state_pressed) {
                oldPressed = true;
            }
        }
        super.setState(stateSet);
        boolean focused = false;
        boolean pressed = false;
        boolean disabled = true;
        for (int i : stateSet) {
            if (i == android.R.attr.state_focused) {
                focused = true;
            } else if (i == android.R.attr.state_pressed) {
                pressed = true;
            } else if (i == android.R.attr.state_enabled) {
                disabled = false;
            }
        }

        if (disabled) {
            unscheduleSelf(mUpdater);
            mRippleColor = mDisabledColor;
            mRippleBgColor = 0;
            mCurrentScale = ACTIVE_SCALE / 2;
            invalidateSelf();
        } else {
            if (pressed) {
                animateToPressed();
                mRippleColor = mRippleBgColor = mPressedColor;
            } else if (oldPressed) {
                mRippleColor = mRippleBgColor = mPressedColor;
                animateToNormal();
            } else if (focused) {
                mRippleColor = mFocusedColor;
                mRippleBgColor = 0;
                mCurrentScale = ACTIVE_SCALE;
                invalidateSelf();
            } else {
                mRippleColor = 0;
                mRippleBgColor = 0;
                mCurrentScale = INACTIVE_SCALE;
                invalidateSelf();
            }
        }
        return true;
    }

    public void animateToPressed() {
        unscheduleSelf(mUpdater);
        if (mCurrentScale < ACTIVE_SCALE) {
            mReverse = false;
            mRunning = true;
            mAnimationInitialValue = mCurrentScale;
            float durationFactor = 1f - ((mAnimationInitialValue - INACTIVE_SCALE) / (ACTIVE_SCALE - INACTIVE_SCALE));
            mDuration = (int) (ANIMATION_DURATION * durationFactor);
            mStartTime = SystemClock.uptimeMillis();
            scheduleSelf(mUpdater, mStartTime + FRAME_DURATION);
        }
    }

    public void animateToNormal() {
        unscheduleSelf(mUpdater);
        if (mCurrentScale > INACTIVE_SCALE) {
            mReverse = true;
            mRunning = true;
            mAnimationInitialValue = mCurrentScale;
            float durationFactor = 1f - ((mAnimationInitialValue - ACTIVE_SCALE) / (INACTIVE_SCALE - ACTIVE_SCALE));
            mDuration = (int) (ANIMATION_DURATION * durationFactor);
            mStartTime = SystemClock.uptimeMillis();
            scheduleSelf(mUpdater, mStartTime + FRAME_DURATION);
        }
    }

    private void updateAnimation(float factor) {
        float initial = mAnimationInitialValue;
        float destination = mReverse ? INACTIVE_SCALE : ACTIVE_SCALE;
        mCurrentScale = initial + (destination - initial) * factor;
        invalidateSelf();
    }

    private final Runnable mUpdater = new Runnable() {

        @Override
        public void run() {

            long currentTime = SystemClock.uptimeMillis();
            long diff = currentTime - mStartTime;
            if (diff < mDuration) {
                float interpolation = mInterpolator.getInterpolation((float) diff / (float) mDuration);
                scheduleSelf(mUpdater, currentTime + FRAME_DURATION);
                updateAnimation(interpolation);
            } else {
                unscheduleSelf(mUpdater);
                mRunning = false;
                updateAnimation(1f);
            }
        }
    };

    @Override
    public void start() {
        //No-Op. We control our own animation
    }

    @Override
    public void stop() {
        //No-Op. We control our own animation
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }
}
