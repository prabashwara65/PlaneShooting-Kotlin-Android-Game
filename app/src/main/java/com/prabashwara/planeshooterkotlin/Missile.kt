package com.prabashwara.planeshooterkotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Missile(context: Context, private val dWidth: Int, private val dHeight: Int) {
    var x: Int = 0
    var y: Int = 0
    var mVelocity: Int = 0
    var missile: Bitmap

    init {
        missile = BitmapFactory.decodeResource(context.resources, R.drawable.missile)
        x = dWidth / 2 - getMissileWidth() / 2
        y = dHeight - GameView.tankHeight - getMissileHeight() / 2
        mVelocity = 50
    }

    fun getMissileWidth(): Int {
        return missile.width
    }

    fun getMissileHeight(): Int {
        return missile.height
    }
}
