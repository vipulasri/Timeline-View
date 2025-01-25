package com.github.vipulasri.timelineview.sample.recyclerview.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.github.vipulasri.timelineview.sample.R

class BorderedCircle : View {

    var fillColor: Int = 0
        set(fillColor) {
            field = fillColor
            initPaint()
            invalidate()
        }

    var borderColor: Int = 0
        set(borderColor) {
            field = borderColor
            initPaint()
            invalidate()
        }

    var borderWidth: Float = 0f
        set(borderWidth) {
            field = borderWidth
            initPaint()
            invalidate()
        }

    private var circlePaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private var borderPaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BorderedCircle)
            fillColor = typedArray.getColor(
                R.styleable.BorderedCircle_fillColor,
                ContextCompat.getColor(context, android.R.color.white)
            )
            borderColor = typedArray.getColor(
                R.styleable.BorderedCircle_borderColor,
                ContextCompat.getColor(context, android.R.color.black)
            )
            borderWidth =
                typedArray.getDimensionPixelSize(R.styleable.BorderedCircle_borderWidth, dpToPx(2f))
                    .toFloat()
            typedArray.recycle()
        }

        initPaint()
    }

    private fun initPaint() {
        circlePaint.color = fillColor

        if (borderWidth > 0) {
            borderPaint.apply {
                color = borderColor
                strokeWidth = borderWidth
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val space = dpToPx(3f)
        val cx = (width / 2).toFloat()
        val cy = (height / 2).toFloat()

        val circleRadius = (width / 2).toFloat() - space
        val borderRadius = (width / 2).toFloat() - (borderWidth / 2)

        // draw circle
        canvas.drawCircle(cx, cy, circleRadius, circlePaint)

        //draw border
        canvas.drawCircle(cx, cy, borderRadius, borderPaint)
    }

    private fun dpToPx(dp: Float): Int {
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
        return px.toInt()
    }

}