package com.example.wallpaperagain

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsControllerCompat
import java.util.logging.Handler

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility or
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        window.statusBarColor = Color.WHITE // Change R.color.yellow to your desired color resource

        // Hide navigation bar after 5 seconds
        android.os.Handler().postDelayed({
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }, 3000) // 5000 is 5 seconds

        setContentView(R.layout.activity_splash)
        android.os.Handler().postDelayed({
            // Start the main activity after 2 seconds
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}