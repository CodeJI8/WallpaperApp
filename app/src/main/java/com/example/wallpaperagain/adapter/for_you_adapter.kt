package com.example.wallpaperagain.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.wallpaperagain.FinalWallpaper
import com.example.wallpaperagain.Models.bomModel
import com.example.wallpaperagain.databinding.ItemBomBinding

class for_you_adapter(private val context: Context, private var wallpaper_list: List<bomModel>):RecyclerView.Adapter<for_you_adapter.viewholder>() {

    init {
        wallpaper_list = wallpaper_list.shuffled() // Shuffle the list in the constructor
    }

    inner class viewholder(binding: ItemBomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val imageview = binding.bomImage

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val binding = ItemBomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewholder(binding)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {


        Glide.with(context).load(wallpaper_list[position].link).into(holder.imageview)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, FinalWallpaper::class.java)
            intent.putExtra("link", wallpaper_list[position].link)
         context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return wallpaper_list.size
    }
}