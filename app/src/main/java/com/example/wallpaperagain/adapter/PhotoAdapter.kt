package com.example.wallpaperagain.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.wallpaperagain.Activity.FinalWallpaper
import com.example.wallpaperagain.Models.Wallpaper_model
import com.example.wallpaperagain.R
import com.example.wallpaperagain.databinding.ItemPhotoBinding


class PhotoAdapter(private val context: Context, private var wallpapers: List<Wallpaper_model>) :
    RecyclerView.Adapter<PhotoAdapter.WallpaperViewholder>() {

    private var wallpapersFiltered: List<Wallpaper_model> = wallpapers


    inner class WallpaperViewholder(private  val binding: ItemPhotoBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(wallpaper: Wallpaper_model) {
            binding.apply {
                Glide.with(context)
                    .load(wallpaper.imageUrl)
                    .apply(RequestOptions().centerCrop())  // Crop the image to fit the ImageView
                    .placeholder(R.drawable.loading)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.imageView)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewholder {
        // Inflate the layout using View Binding
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WallpaperViewholder(binding)
    }


    override fun getItemCount(): Int {
        return wallpapersFiltered.size
    }

    override fun onBindViewHolder(holder: WallpaperViewholder, position: Int) {
        val wallpaper = wallpapersFiltered[position]

        // Bind the data to the ViewHolder
        holder.bind(wallpaper)

        // Handle click event
        holder.itemView.setOnClickListener {
            val intent = Intent(context, FinalWallpaper::class.java)
            intent.putExtra("link", wallpaper.imageUrl) // Pass the image link to the next activity
            context.startActivity(intent)
        }

    }

}



