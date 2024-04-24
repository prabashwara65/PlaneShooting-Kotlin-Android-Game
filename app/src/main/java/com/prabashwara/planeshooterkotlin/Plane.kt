package com.prabashwara.planeshooterkotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.*

open class Plane(context: Context,  private val dWidth: Int) {
    private val plane = arrayOfNulls<Bitmap>(15)
    var planeX: Int = 0
    var planeY: Int = 0
    var velocity: Int = 0
    var planeFrame: Int = 0
    val random: Random = Random()

    init {
        plane[0] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_1)
        plane[1] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_2)
        plane[2] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_3)
        plane[3] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_4)
        plane[4] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_5)
        plane[5] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_6)
        plane[6] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_7)
        plane[7] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_8)
        plane[8] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_9)
        plane[9] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_10)
        plane[10] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_11)
        plane[11] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_12)
        plane[12] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_13)
        plane[13] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_14)
        plane[14] = BitmapFactory.decodeResource(context.resources, R.drawable.plane_15)
        resetPosition()
    }

    open fun getBitmap(): Bitmap? {
        return plane[planeFrame]
    }

    open fun getWidth(): Int {
        return plane[0]?.width ?: 0
    }

    open fun getHeight(): Int {
        return plane[0]?.height ?: 0
    }

    open fun resetPosition() {
        planeX = GameView.tankHeight + random.nextInt(1200)
        planeY = random.nextInt(300)
        velocity = 8 + random.nextInt(13)
        planeFrame = 0
    }
}
