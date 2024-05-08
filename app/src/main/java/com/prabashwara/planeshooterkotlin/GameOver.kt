package com.prabashwara.planeshooterkotlin


import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView

class GameOver : Activity() {

    private lateinit var tvScore: TextView
    private lateinit var tvPersonalBest: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)
        val score = intent.extras!!.getInt("score")
        val pref = getSharedPreferences("MyPref", 0)

        //Shared Preferences usage to
        // insert data to internal
        // storage and retrieve and
        // comparison
        var scoreSP = pref.getInt("scoreSP", 0)
        val editor = pref.edit()
        if (score > scoreSP) {
            scoreSP = score
            editor.putInt("scoreSP", scoreSP)
            editor.apply()
        }
        tvScore = findViewById(R.id.tvScore)
        tvPersonalBest = findViewById(R.id.tvPersonalBest)
        tvScore.text = score.toString()
        tvPersonalBest.text = scoreSP.toString()
    }

    fun restart(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun exit(view: View) {
        finish()
    }
}
