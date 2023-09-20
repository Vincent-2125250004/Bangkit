package com.dicoding.submissions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val iv_Splash : ImageView = findViewById(R.id.iv_Splash)
        iv_Splash.alpha =0f
        iv_Splash.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent(this,MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }
    }


}