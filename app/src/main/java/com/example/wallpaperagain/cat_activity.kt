package com.example.wallpaperagain

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperagain.Models.add_models

import com.example.wallpaperagain.adapter.latest_adapter
import com.example.wallpaperagain.databinding.ActivityCatBinding
import com.google.firebase.firestore.FirebaseFirestore

class cat_activity : AppCompatActivity() {
    private lateinit var binding: ActivityCatBinding
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

        binding = ActivityCatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getProductData(intent.getStringExtra("cat"))
    }
    private fun getProductData(category: String?) {
        val list = ArrayList<add_models>()
        FirebaseFirestore.getInstance().collection("wallpapers").whereEqualTo("category", category)
            .get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val imageUrls = doc.get("link") as? String

                    val model = add_models(

                        images = imageUrls
                    )
                    list.add(model)
                    val categoryText = doc.getString("category")
                    binding.catText.text = categoryText
                }
                val cat_Adapter = latest_adapter(this, list)
                binding.catWiseRecycler.adapter = cat_Adapter
                binding.catWiseRecycler.layoutManager = GridLayoutManager(this, 3)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }

    }
}