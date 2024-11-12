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

package org.adw.library.widgets.discreteseekbar.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import org.adw.library.widgets.discreteseekbar.R;
import org.adw.library.widgets.discreteseekbar.internal.compat.SeekBarCompat;
import org.adw.library.widgets.discreteseekbar.internal.drawable.MarkerDrawable;

/**
 * {@link ViewGroup} to be used as the real indicator.
 * <p>
 * I've used this to be able to accommodate the TextView
 * and the {@link MarkerDrawable}
 * with the required positions and offsets
 * </p>
 *
 * @hide
 */
public class Marker extends ViewGroup implements MarkerDrawable.MarkerAnimationListener {
    private static final int PADDING_DP = 4;
    private static final int ELEVATION_DP = 8;
    //The TextView to show the info
    private TextView mNumber;
    //The max width of this View
    private int mWidth;
    //some distance between the thumb and our bubble marker.
    //This will be added to our measured height
    private int mSeparation;
    MarkerDrawable mMarkerDrawable;

    public Marker(Context context, AttributeSet attrs, int defStyleAttr, String maxValue, int thumbSize, int separation) {
        super(context, attrs, defStyleAttr);
        //as we're reading the parent DiscreteSeekBar attributes, it may wrongly set this view's visibility.
        setVisibility(View.VISIBLE);
        
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DiscreteSeekBar,
                R.attr.discreteSeekBarStyle, R.style.Widget_DiscreteSeekBar);

        int padding = (int) (PADDING_DP * displayMetrics.density) * 2;
        int textAppearanceId = a.getResourceId(R.styleable.DiscreteSeekBar_dsb_indicatorTextAppearance,
                R.style.Widget_DiscreteIndicatorTextAppearance);
        mNumber = new TextView(context);
        //Add some padding to this textView so the bubble has some space to breath
        mNumber.setPadding(padding, 0, padding, 0);
        mNumber.setTextAppearance(context, textAppearanceId);
        mNumber.setGravity(Gravity.CENTER);
        mNumber.setText(maxValue);
        mNumber.setMaxLines(1);
        mNumber.setSingleLine(true);
        SeekBarCompat.setTextDirection(mNumber, TEXT_DIRECTION_LOCALE);
        mNumber.setVisibility(View.INVISIBLE);

        //add some padding for the elevation shadow not to be clipped
        //I'm sure there are better ways of doing this...
        setPadding(padding, padding, padding, padding);

        resetSizes(maxValue);

        mSeparation = separation;
        ColorStateList color = a.getColorStateList(R.styleable.DiscreteSeekBar_dsb_indicatorColor);
        mMarkerDrawable = new MarkerDrawable(color, thumbSize);
        mMarkerDrawable.setCallback(this);
        mMarkerDrawable.setMarkerListener(this);
        mMarkerDrawable.setExternalOffset(padding);

        //Elevation for anroid 5+
        float elevation = a.getDimension(R.styleable.DiscreteSeekBar_dsb_indicatorElevation, ELEVATION_DP * displayMetrics.density);
        ViewCompat.setElevation(this, elevation);
        SeekBarCompat.setOutlineProvider(this, mMarkerDrawable);
        a.recycle();
    }

    public void resetSizes(String maxValue) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //Account for negative numbers... is there any proper way of getting the biggest string between our range????
        mNumber.setText("-" + maxValue);
        //Do a first forced measure call for the TextView (with the biggest text content),
        //to calculate the max width and use always the same.
        //this avoids the TextView from shrinking and growing when the text content changes
        int wSpec = MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, MeasureSpec.AT_MOST);
        int hSpec = MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, MeasureSpec.AT_MOST);
        mNumber.measure(wSpec, hSpec);
        mWidth = Math.max(mNumber.getMeasuredWidth(), mNumber.getMeasuredHeight());
        removeView(mNumber);
        addView(mNumber, new FrameLayout.LayoutParams(mWidth, mWidth, Gravity.LEFT | Gravity.TOP));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mMarkerDrawable.draw(canvas);
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSize = mWidth + getPaddingLeft() + getPaddingRight();
        int heightSize = mWidth + getPaddingTop() + getPaddingBottom();
        //This diff is the basic calculation of the difference between
        //a square side size and its diagonal
        //this helps us account for the visual offset created by MarkerDrawable
        //when leaving one of the corners un-rounded
        int diff = (int) ((1.41f * mWidth) - mWidth) / 2;
        setMeasuredDimension(widthSize, heightSize + diff + mSeparation);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getWidth() - getPaddingRight();
        int bottom = getHeight() - getPaddingBottom();
        //the TetView is always layout at the top
        mNumber.layout(left, top, left + mWidth, top + mWidth);
        //the MarkerDrawable uses the whole view, it will adapt itself...
        // or it seems so...
        mMarkerDrawable.setBounds(left, top, right, bottom);
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mMarkerDrawable || super.verifyDrawable(who);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //HACK: Sometimes, the animateOpen() call is made before the View is attached
        //so the drawable cannot schedule itself to run the animation
        //I think we can call it here safely.
        //I've seen it happen in android 2.3.7
        animateOpen();
    }

    public void setValue(CharSequence value) {
        mNumber.setText(value);
    }

    public CharSequence getValue() {
        return mNumber.getText();
    }

    public void animateOpen() {
        mMarkerDrawable.stop();
        mMarkerDrawable.animateToPressed();
    }

    public void animateClose() {
        mMarkerDrawable.stop();
        mNumber.setVisibility(View.INVISIBLE);
        mMarkerDrawable.animateToNormal();
    }

    @Override
    public void onOpeningComplete() {
        mNumber.setVisibility(View.VISIBLE);
        if (getParent() instanceof MarkerDrawable.MarkerAnimationListener) {
            ((MarkerDrawable.MarkerAnimationListener) getParent()).onOpeningComplete();
        }
    }

    @Override
    public void onClosingComplete() {
        if (getParent() instanceof MarkerDrawable.MarkerAnimationListener) {
            ((MarkerDrawable.MarkerAnimationListener) getParent()).onClosingComplete();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mMarkerDrawable.stop();
    }

    public void setColors(int startColor, int endColor) {
        mMarkerDrawable.setColors(startColor, endColor);
    }
}
