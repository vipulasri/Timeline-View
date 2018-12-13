package com.github.vipulasri.timelineview.sample.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.github.vipulasri.timelineview.sample.R

class BorderedCircle : View {

    var mFillColor: Int = 0
        set(fillColor) {
            field = fillColor
            initPaint()
            invalidate()
        }
    var mBorderColor: Int = 0
        set(borderColor) {
            field = borderColor
            initPaint()
            invalidate()
        }
    var mBorderWidth: Float = 0f
        set(borderWidth) {
            field = borderWidth
            initPaint()
            invalidate()
        }

    private var mCirclePaint = Paint()
    private var mBorderPaint = Paint()

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if(attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BorderedCircle)
            mFillColor = typedArray.getColor(R.styleable.BorderedCircle_fillColor, ContextCompat.getColor(context, android.R.color.white))
            mBorderColor = typedArray.getColor(R.styleable.BorderedCircle_borderColor, ContextCompat.getColor(context, android.R.color.black))
            mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.BorderedCircle_borderWidth, dpToPx(2f)).toFloat()
            typedArray.recycle()
        }

        initPaint()
    }

    private fun initPaint() {
        mCirclePaint.isAntiAlias = true
        mCirclePaint.color = mFillColor
        mCirclePaint.style = Paint.Style.FILL

        if(mBorderWidth>0) {
            mBorderPaint.isAntiAlias = true
            mBorderPaint.color = mBorderColor
            mBorderPaint.strokeWidth = mBorderWidth
            mBorderPaint.style = Paint.Style.STROKE
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val cx = (width/2).toFloat()
        val cy = (height/2).toFloat()

        val circleRadius = (width/2).toFloat()
        val borderRadius = (width/2).toFloat() - (mBorderWidth/2)

        // draw circle
        canvas?.drawCircle(cx, cy, circleRadius, mCirclePaint)

        //draw border
        canvas?.drawCircle(cx, cy, borderRadius, mBorderPaint)
    }

    private fun dpToPx(dp: Float): Int {
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
        return px.toInt()
    }

}