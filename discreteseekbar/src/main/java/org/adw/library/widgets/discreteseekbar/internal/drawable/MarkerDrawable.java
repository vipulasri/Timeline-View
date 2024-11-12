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
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;

/**
 * Implementation of {@link StateDrawable} to draw a morphing marker symbol.
 * <p>
 * It's basically an implementation of an {@link Animatable} Drawable with the following details:
 * </p>
 * <ul>
 * <li>Animates from a circle shape to a "marker" shape just using a RoundRect</li>
 * <li>Animates color change from the normal state color to the pressed state color</li>
 * <li>Uses a {@link Path} to also serve as Outline for API>=21</li>
 * </ul>
 *
 * @hide
 */
public class MarkerDrawable extends StateDrawable implements Animatable {
    private static final long FRAME_DURATION = 1000 / 60;
    private static final int ANIMATION_DURATION = 250;

    private float mCurrentScale = 0f;
    private Interpolator mInterpolator;
    private long mStartTime;
    private boolean mReverse = false;
    private boolean mRunning = false;
    private int mDuration = ANIMATION_DURATION;
    //size of the actual thumb drawable to use as circle state size
    private float mClosedStateSize;
    //value to store que current scale when starting an animation and interpolate from it
    private float mAnimationInitialValue;
    //extra offset directed from the View to account
    //for its internal padding between circle state and marker state
    private int mExternalOffset;
    //colors for interpolation
    private int mStartColor;//Color when the Marker is OPEN
    private int mEndColor;//Color when the arker is CLOSED

    Path mPath = new Path();
    RectF mRect = new RectF();
    Matrix mMatrix = new Matrix();
    private MarkerAnimationListener mMarkerListener;

    public MarkerDrawable(@NonNull ColorStateList tintList, int closedSize) {
        super(tintList);
        mInterpolator = new AccelerateDecelerateInterpolator();
        mClosedStateSize = closedSize;
        mStartColor = tintList.getColorForState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, tintList.getDefaultColor());
        mEndColor = tintList.getDefaultColor();

    }

    public void setExternalOffset(int offset) {
        mExternalOffset = offset;
    }

    /**
     * The two colors that will be used for the seek thumb.
     *
     * @param startColor Color used for the seek thumb
     * @param endColor   Color used for popup indicator
     */
    public void setColors(int startColor, int endColor) {
        mStartColor = startColor;
        mEndColor = endColor;
    }

    @Override
    void doDraw(Canvas canvas, Paint paint) {
        if (!mPath.isEmpty()) {
            paint.setStyle(Paint.Style.FILL);
            int color = blendColors(mStartColor, mEndColor, mCurrentScale);
            paint.setColor(color);
            canvas.drawPath(mPath, paint);
        }
    }

    public Path getPath() {
        return mPath;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        computePath(bounds);
    }

    private void computePath(Rect bounds) {
        final float currentScale = mCurrentScale;
        final Path path = mPath;
        final RectF rect = mRect;
        final Matrix matrix = mMatrix;

        path.reset();
        int totalSize = Math.min(bounds.width(), bounds.height());

        float initial = mClosedStateSize;
        float destination = totalSize;
        float currentSize = initial + (destination - initial) * currentScale;

        float halfSize = currentSize / 2f;
        float inverseScale = 1f - currentScale;
        float cornerSize = halfSize * inverseScale;
        float[] corners = new float[]{halfSize, halfSize, halfSize, halfSize, halfSize, halfSize, cornerSize, cornerSize};
        rect.set(bounds.left, bounds.top, bounds.left + currentSize, bounds.top + currentSize);
        path.addRoundRect(rect, corners, Path.Direction.CCW);
        matrix.reset();
        matrix.postRotate(-45, bounds.left + halfSize, bounds.top + halfSize);
        matrix.postTranslate((bounds.width() - currentSize) / 2, 0);
        float hDiff = (bounds.bottom - currentSize - mExternalOffset) * inverseScale;
        matrix.postTranslate(0, hDiff);
        path.transform(matrix);
    }

    private void updateAnimation(float factor) {
        float initial = mAnimationInitialValue;
        float destination = mReverse ? 0f : 1f;
        mCurrentScale = initial + (destination - initial) * factor;
        computePath(getBounds());
        invalidateSelf();
    }

    public void animateToPressed() {
        unscheduleSelf(mUpdater);
        mReverse = false;
        if (mCurrentScale < 1) {
            mRunning = true;
            mAnimationInitialValue = mCurrentScale;
            float durationFactor = 1f - mCurrentScale;
            mDuration = (int) (ANIMATION_DURATION * durationFactor);
            mStartTime = SystemClock.uptimeMillis();
            scheduleSelf(mUpdater, mStartTime + FRAME_DURATION);
        } else {
            notifyFinishedToListener();
        }
    }

    public void animateToNormal() {
        mReverse = true;
        unscheduleSelf(mUpdater);
        if (mCurrentScale > 0) {
            mRunning = true;
            mAnimationInitialValue = mCurrentScale;
            float durationFactor = 1f - mCurrentScale;
            mDuration = ANIMATION_DURATION - (int) (ANIMATION_DURATION * durationFactor);
            mStartTime = SystemClock.uptimeMillis();
            scheduleSelf(mUpdater, mStartTime + FRAME_DURATION);
        } else {
            notifyFinishedToListener();
        }
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
                notifyFinishedToListener();
            }
        }
    };

    public void setMarkerListener(MarkerAnimationListener listener) {
        mMarkerListener = listener;
    }

    private void notifyFinishedToListener() {
        if (mMarkerListener != null) {
            if (mReverse) {
                mMarkerListener.onClosingComplete();
            } else {
                mMarkerListener.onOpeningComplete();
            }
        }
    }

    @Override
    public void start() {
        //No-Op. We control our own animation
    }

    @Override
    public void stop() {
        unscheduleSelf(mUpdater);
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    private static int blendColors(int color1, int color2, float factor) {
        final float inverseFactor = 1f - factor;
        float a = (Color.alpha(color1) * factor) + (Color.alpha(color2) * inverseFactor);
        float r = (Color.red(color1) * factor) + (Color.red(color2) * inverseFactor);
        float g = (Color.green(color1) * factor) + (Color.green(color2) * inverseFactor);
        float b = (Color.blue(color1) * factor) + (Color.blue(color2) * inverseFactor);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }


    /**
     * A listener interface to porpagate animation events
     * This is the "poor's man" AnimatorListener for this Drawable
     */
    public interface MarkerAnimationListener {
        public void onClosingComplete();

        public void onOpeningComplete();
    }
}
