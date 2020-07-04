package com.Samy.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ModeSlection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_slection)
    }
    fun easyMode(view:View){
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("mode","easy")
        startActivity(intent)
    }

    fun mediumMode(view:View){
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("mode","medium")
        startActivity(intent)
    }

    fun hardMode(view:View){
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("mode","hard")
        startActivity(intent)
    }
}
