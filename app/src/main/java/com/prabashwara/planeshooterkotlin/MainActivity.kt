package com.prabashwara.planeshooterkotlin



import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startGame(v: View) {
        Log.i("ImageButton", "clicked")
        val intent = Intent(this, StartGame::class.java)
        startActivity(intent)
        finish()
    }
}
