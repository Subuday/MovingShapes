package com.muzzlyworld.movingshapes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.*
import kotlin.random.Random

private val TAG = MovingShapesView::class.java.simpleName

class MovingShapesView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 3F
    }

    private val circlesCoordinator = CirclesCoordinator(paint.strokeWidth.roundToInt())

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        circlesCoordinator.init(left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            circlesCoordinator.updateCirclesPositions()
            circlesCoordinator.circles.forEach {
                paint.color = it.color
                canvas.drawCircle(it.center.x, it.center.y, it.radius.toFloat(), paint)
            }
        }
        postInvalidateOnAnimation()
    }
}