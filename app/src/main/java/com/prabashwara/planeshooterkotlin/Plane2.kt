package com.prabashwara.planeshooterkotlin



import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Plane2(context: Context, dWidth: Int) : Plane(context, dWidth) {

    private val plane = arrayOfNulls<Bitmap>(10)

    init {
        plane[0] = BitmapFactory.decodeResource(context.resources, R.drawable.plane2_1)
        plane[1] = BitmapFactory.decodeResource(context.resources, R.drawable.plane2_2)
        plane[2] = BitmapFactory.decodeResource(context.resources, R.drawable.plane2_3)
        plane[3] = BitmapFactory.decodeResource(context.resources, R.drawable.plane2_4)
        plane[4] = BitmapFactory.decodeResource(context.resources, R.drawable.plane2_5)
        plane[5] = BitmapFactory.decodeResource(context.resources, R.drawable.plane2_6)
        plane[6] = BitmapFactory.decodeResource(context.resources, R.drawable.plane2_7)
        plane[7] = BitmapFactory.decodeResource(context.resources, R.drawable.plane2_8)
        plane[8] = BitmapFactory.decodeResource(context.resources, R.drawable.plane2_9)
        plane[9] = BitmapFactory.decodeResource(context.resources, R.drawable.plane2_10)
        resetPosition()
    }

    override
    fun getBitmap(): Bitmap? {
        return plane[planeFrame]
    }

    override fun getWidth(): Int {
        return plane[0]?.width ?: 0
    }

    override fun getHeight(): Int {
        return plane[0]?.height ?: 0
    }

    override fun resetPosition() {
        planeX = -(200 + random.nextInt(1500))
        planeY = random.nextInt(400)
        velocity = 5 + random.nextInt(21)
    }
}
