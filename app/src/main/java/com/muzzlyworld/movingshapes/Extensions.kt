package com.muzzlyworld.movingshapes

import android.graphics.Color
import kotlin.random.Random

fun Random.nextColor() = Color.argb(
    255,
    nextInt(0, 256),
    nextInt(0, 256),
    nextInt(0, 256)
)

fun Random.nextAngel() = Random.nextInt(0, 360)