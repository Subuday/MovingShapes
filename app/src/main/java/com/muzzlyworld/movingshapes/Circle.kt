package com.muzzlyworld.movingshapes

import android.graphics.PointF
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.random.Random

typealias Circles = List<Circle>

class Circle private constructor(
    val center: PointF,
    val color: Int,
    val radius: Int,
    val speed: Int,
    private var directionAngel: Int
) {

    fun getDirectionAngel() = directionAngel

    fun changeDirection() {
        directionAngel = (directionAngel + 180) % 360
    }

    companion object {
        fun randomCircle(strokeWidth: Int, left: Int, top: Int, right: Int, bottom: Int) : Circle {
            val width = right - left
            val height = bottom - left

            val radius = generateCircleRadius(width, height)
            val centerX = Random.nextInt(left + radius + strokeWidth, right - radius - strokeWidth).toFloat()
            val centerY = Random.nextInt(top + radius + strokeWidth, bottom - radius - strokeWidth).toFloat()
            val center = PointF(centerX, centerY)
            val speed = generateCircleSpeed(width, height)
            val color = Random.nextColor()
            val directionAngel = Random.nextAngel()

            return Circle(center, color, radius, speed, directionAngel)
        }

        private fun generateCircleSpeed(width: Int, height: Int): Int {
            val maxSpeed = (min(width, height) * 0.02).roundToInt()
            return if(maxSpeed == 0) 1 else Random.nextInt(1, maxSpeed)
        }

        private fun generateCircleRadius(width: Int, height: Int): Int {
            val maxRadius = (min(width, height) * 0.1).roundToInt()
            return if(maxRadius > 10) Random.nextInt(10, maxRadius) else 10
        }
    }
}