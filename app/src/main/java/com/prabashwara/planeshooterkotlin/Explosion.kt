package com.prabashwara.planeshooterkotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Explosion(private val context: Context) {
    private val explosion = arrayOfNulls<Bitmap>(9)
    var explosionFrame = 0
    var explosionX = 0
    var explosionY = 0

    init {
        explosion[0] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion0)
        explosion[1] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion1)
        explosion[2] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion2)
        explosion[3] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion3)
        explosion[4] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion4)
        explosion[5] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion5)
        explosion[6] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion6)
        explosion[7] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion7)
        explosion[8] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion8)
    }

    fun getExplosion(explosionFrame: Int): Bitmap? {
        return explosion[explosionFrame]
    }

    fun getExplosionWidth(): Int {
        return explosion[0]?.width ?: 0
    }

    fun getExplosionHeight(): Int {
        return explosion[0]?.height ?: 0
    }
}
