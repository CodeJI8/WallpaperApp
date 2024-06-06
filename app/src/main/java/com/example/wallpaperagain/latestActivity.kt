package com.example.wallpaperagain

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperagain.Models.bomModel
import com.example.wallpaperagain.adapter.for_you_adapter
import com.example.wallpaperagain.databinding.ActivityLatestBinding
import com.google.firebase.firestore.FirebaseFirestore

class latestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLatestBinding
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

        binding = ActivityLatestBinding.inflate(layoutInflater)
        binding.homeTxt.setOnClickListener {

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        getBom()

        setContentView(binding.root)
    }

    private fun getBom() {
        val list = ArrayList<bomModel>()
        FirebaseFirestore.getInstance().collection("wallpapers").get().addOnSuccessListener { querySnapshot ->
            for (doc in querySnapshot.documents) {
                val categoryImageString = doc.getString("link")
                val categoryImageUri = Uri.parse(categoryImageString)
                val catModel = bomModel(
                    doc.getString("link"),

                    )
                list.add(catModel)
            }
            binding.bomRecycler.adapter = for_you_adapter(this, list)
            binding.bomRecycler.layoutManager = GridLayoutManager(this, 2)

        }.addOnFailureListener { exception ->
            Log.e("TAG", "Error getting documents: ", exception)
        }
    }
}