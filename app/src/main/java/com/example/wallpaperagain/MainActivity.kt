package com.example.wallpaperagain

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cat_model
import com.example.wallpaperagain.adapter.for_you_adapter
import com.example.wallpaperagain.Models.bomModel
import com.example.wallpaperagain.adapter.category_adapter
import com.example.wallpaperagain.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
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

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.latestTxt.setOnClickListener {

            val intent = Intent(this@MainActivity,latestActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right)
        }
        setContentView(binding.root)
        getCat()
        getBom()

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
            binding.bomRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        }.addOnFailureListener { exception ->
            Log.e("TAG", "Error getting documents: ", exception)
        }
    }


    private fun getCat() {
        val list = ArrayList<cat_model>()
        FirebaseFirestore.getInstance().collection("categories").get().addOnSuccessListener { querySnapshot ->
            for (doc in querySnapshot.documents) {
                val categoryImageString = doc.getString("link")
                val categoryImageUri = Uri.parse(categoryImageString)
                val catModel = cat_model(
                    doc.getString("category"),
                    categoryImageUri
                )
                list.add(catModel)
            }
            binding.catRecycler.adapter = category_adapter(this, list)
            binding.catRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }.addOnFailureListener { exception ->
            Log.e("TAG", "Error getting documents: ", exception)
        }
    }


}