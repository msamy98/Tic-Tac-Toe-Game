package com.Samy.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
    }
    fun playButtonClick(view:View){
        startActivity(Intent(this,ModeSlection::class.java))
    }
    fun settingsButtonClick(view:View){
        startActivity(Intent(this,SettingsActivity::class.java))
    }

}
