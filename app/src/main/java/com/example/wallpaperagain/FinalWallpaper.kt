package com.example.wallpaperagain

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.transition.Transition
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.wallpaperagain.databinding.ActivityFinalwallpaperBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File

class FinalWallpaper : AppCompatActivity() {

    private lateinit var binding: ActivityFinalwallpaperBinding
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalwallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initStatusBar()
        initViews()
    }

    private fun initStatusBar() {
        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility or
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        window.statusBarColor = Color.WHITE // Change R.color.yellow to your desired color resource

        // Hide navigation bar after 5 seconds
        android.os.Handler().postDelayed({
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }, 3000) // 5000 is 5 seconds

    }

    private fun initViews() {
        url = intent.getStringExtra("link") ?: ""
        Glide.with(this).load(url).into(binding.finalWallpaper)

        binding.downloadBtn.setOnClickListener { downloadWallpaper()
            Toast.makeText(this, "Wallpaper downloading", Toast.LENGTH_SHORT).show()}
        binding.setBtn.setOnClickListener { setWallpaper()
            Toast.makeText(this, "Setting up the wallpaper", Toast.LENGTH_SHORT).show()}
    }

    private fun downloadWallpaper() {

        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
        request.setTitle("Wallpaper Download")
        request.setDescription("Downloading wallpaper...")
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "wallpaper.jpg")

        val manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)

        Toast.makeText(this, "Wallpaper download started", Toast.LENGTH_SHORT).show()
    }

    private fun setWallpaper() = lifecycleScope.launch(Dispatchers.IO) {
        try {
            val future = Glide.with(this@FinalWallpaper)
                .asBitmap()
                .load(url)
                .submit()

            val bitmap = runBlocking { future.get() }

            withContext(Dispatchers.Main) {
                val displayWidth = Resources.getSystem().displayMetrics.widthPixels
                val displayHeight = Resources.getSystem().displayMetrics.heightPixels
                val scaledBitmap = Bitmap.createScaledBitmap(bitmap, displayWidth, displayHeight, true)

                val wallpaperManager = WallpaperManager.getInstance(this@FinalWallpaper)
                wallpaperManager.setBitmap(scaledBitmap)

                Toast.makeText(this@FinalWallpaper, "Wallpaper set successfully", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@FinalWallpaper, "Error setting wallpaper: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}