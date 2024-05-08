package com.prabashwara.planeshooterkotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.media.SoundPool
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import java.util.concurrent.CopyOnWriteArrayList

class GameView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var background: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.nightsky)
    private var tank: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.rocket2)
    private val rect: Rect
    private var planes = CopyOnWriteArrayList<Plane>()
    private var planes2 = CopyOnWriteArrayList<Plane2>()
    private var missiles = CopyOnWriteArrayList<Missile>()
    private var explosions = CopyOnWriteArrayList<Explosion>()
    private val handler: Handler = Handler()
    private val runnable: Runnable = Runnable { invalidate() }
    private val UPDATE_MILLIS: Long = 30
    private var count = 0
    private lateinit var sp: SoundPool
    private var fire = 0
    private var point = 0
    private val scorePaint = Paint()
    private val healthPaint = Paint()
    private val TEXT_SIZE = 60
    private var life = 10
    private var dWidth = 0
    private var dHeight = 0

    init {
        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        dWidth = size.x
        dHeight = size.y
        rect = Rect(0, 0, dWidth, dHeight)

        for (i in 0 until 2) {
            planes.add(Plane(context, dWidth))
            planes2.add(Plane2(context, dWidth))
        }

        tankWidth = tank.width
        tankHeight = tank.height

        sp = SoundPool.Builder().setMaxStreams(3).build()
        fire = sp.load(context, R.raw.fire, 1)
        point = sp.load(context, R.raw.point, 1)

        scorePaint.color = Color.RED
        scorePaint.textSize = TEXT_SIZE.toFloat()
        scorePaint.textAlign = Paint.Align.LEFT

        healthPaint.color = Color.GREEN
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(background, null, rect, null)

        for (plane in planes) {
            plane.getBitmap()?.let { canvas.drawBitmap(it, null, Rect(plane.planeX, plane.planeY, plane.planeX + plane.getWidth(), plane.planeY + plane.getHeight()), null) }
            plane.planeFrame++
            if (plane.planeFrame > 14) {
                plane.planeFrame = 0
            }
            plane.planeX -= plane.velocity
            if (plane.planeX < -plane.getWidth()) {
                plane.resetPosition()
                //Life is decreasing when planes not hit by missiles
                //planes are missing life will lose
                life--
                if (life == 0) {
                    gameOver()
                }
            }
        }

        for (plane2 in planes2) {
            plane2.getBitmap()?.let { canvas.drawBitmap(it, null, Rect(plane2.planeX, plane2.planeY, plane2.planeX + plane2.getWidth(), plane2.planeY + plane2.getHeight()), null) }
            plane2.planeFrame++
            if (plane2.planeFrame > 9) {
                plane2.planeFrame = 0
            }
            plane2.planeX += plane2.velocity
            if (plane2.planeX > dWidth + plane2.getWidth()) {
                plane2.resetPosition()
                //Life is decreasing when planes not hit by missiles
                life--
                if (life == 0) {
                    gameOver()
                }
            }
        }

        for (missile in missiles) {
            if (missile.y > -missile.getMissileHeight()) {
                missile.y -= missile.mVelocity
                canvas.drawBitmap(missile.missile, null, Rect(missile.x, missile.y, missile.x + missile.getMissileWidth(), missile.y + missile.getMissileHeight()), null)

                for (plane in planes) {
                    if (missile.x >= plane.planeX && missile.x + missile.getMissileWidth() <= plane.planeX + plane.getWidth() &&
                        missile.y >= plane.planeY && missile.y <= plane.planeY + plane.getHeight()) {
                        handleCollision(plane)
                    }
                }

                for (plane2 in planes2) {
                    if (missile.x >= plane2.planeX && missile.x + missile.getMissileWidth() <= plane2.planeX + plane2.getWidth() &&
                        missile.y >= plane2.planeY && missile.y <= plane2.planeY + plane2.getHeight()) {
                        handleCollision(plane2)
                    }
                }
            } else {
                missiles.remove(missile)
            }
        }

        for (explosion in explosions) {
            explosion.getExplosion(explosion.explosionFrame)?.let { canvas.drawBitmap(it, explosion.explosionX.toFloat(), explosion.explosionY.toFloat(), null) }
            explosion.explosionFrame++
            if (explosion.explosionFrame > 8) {
                explosions.remove(explosion)
            }
        }

        canvas.drawBitmap(tank, (dWidth / 2 - tankWidth / 2).toFloat(), (dHeight - tankHeight).toFloat(), null)
        canvas.drawText("Pt: " + count * 10, 0f, TEXT_SIZE.toFloat(), scorePaint)
        canvas.drawRect((dWidth - 110).toFloat(), 10f, (dWidth - 110 + 10 * life).toFloat(), TEXT_SIZE.toFloat(), healthPaint)

        handler.postDelayed(runnable, UPDATE_MILLIS)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        val action = event.action
        if (action == MotionEvent.ACTION_DOWN) {
            if (touchX >= dWidth / 2 - tankWidth / 2 && touchX <= dWidth / 2 + tankWidth / 2 && touchY >= dHeight - tankHeight) {
                Log.i("Tank", "is tapped")
                if (missiles.size < 3) {
                    val m = Missile(context, dWidth, dHeight)
                    missiles.add(m)
                    if (fire != 0) {
                        sp.play(fire, 1f, 1f, 0, 0, 1f)
                    }
                }
            }
        }
        return true
    }

    private fun gameOver() {
        //passing count with intent
        val intent = Intent(context, GameOver::class.java)
        intent.putExtra("score", count * 10)
        context.startActivity(intent)
        (context as Activity).finish()
    }

    private fun handleCollision(plane: Plane) {
        val explosion = Explosion(context)
        explosion.explosionX = plane.planeX + plane.getWidth() / 2 - explosion.getExplosionWidth() / 2
        explosion.explosionY = plane.planeY + plane.getHeight() / 2 - explosion.getExplosionHeight() / 2
        explosions.add(explosion)
        plane.resetPosition()
        //increase count when missile hit on plane
        count++
        ////increase player life when missile hit on plane

        if (point != 0) {
            sp.play(point, 1f, 1f, 0, 0, 1f)
        }
    }

    companion object {
        private var tankWidth = 0
        var tankHeight = 0
    }
}
