package com.muzzlyworld.movingshapes

import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

private val TAG = CirclesCoordinator::class.java.name

class CirclesCoordinator(private val strokeWidth: Int) {

    private val _circles = mutableListOf<Circle>()
    val circles: Circles = _circles

    private var containerLeft = 0
    private var containerTop = 0
    private var containerRight = 0
    private var containerBottom = 0

    fun init(left: Int, top: Int, right: Int, bottom: Int) {
        _circles.clear()
        containerLeft = left
        containerRight = right
        containerTop = top
        containerBottom = bottom
        generateCircles(left, top, right, bottom)
    }

    fun updateCirclesPositions() {
        fun Int.toRadian(): Float = toFloat() / 180F * Math.PI.toFloat()

        _circles.forEach {
            it.center.x += it.speed * cos(it.getDirectionAngel().toRadian())
            it.center.y += it.speed * sin(it.getDirectionAngel().toRadian())

            val hasCollisionWithContainer = hasCollisionWithContainer(it)
            val hasCollisionWithOthers = _circles.filter { otherCircle -> it != otherCircle }
                .any { otherCircle -> hasCollision(it, otherCircle) }

            if(hasCollisionWithContainer || hasCollisionWithOthers) it.changeDirection()
        }
    }

    private fun generateCircles(left: Int, top: Int, right: Int, bottom: Int) {
        _circles.clear()
        val maxCircles = Random.nextInt(1, 30)
        while (_circles.size < maxCircles) {
            val circle = Circle.randomCircle(strokeWidth, left, top, right, bottom)
            _circles.any { hasCollision(circle, it) }
                .takeUnless { it }
                ?.let { _circles += circle }
        }
    }

    private fun hasCollision(circle: Circle, other: Circle): Boolean {
        val distanceBetweenCirclesX = other.center.x - circle.center.x
        val distanceBetweenCircleY = other.center.y - circle.center.y
        val distanceBetweenCircles =
            sqrt(distanceBetweenCirclesX.pow(2) + distanceBetweenCircleY.pow(2))
        return (other.radius + strokeWidth + circle.radius + strokeWidth) >= distanceBetweenCircles
    }

    private fun hasCollisionWithContainer(circle: Circle): Boolean {
        return (containerTop >= circle.center.y - circle.radius - strokeWidth) ||
                (containerLeft >= circle.center.x - circle.radius - strokeWidth) ||
                (containerRight <= circle.center.x + circle.radius - strokeWidth) ||
                (containerBottom <= circle.center.y + circle.radius - strokeWidth)
    }
}