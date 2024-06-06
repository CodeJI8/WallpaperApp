package com.example.wallpaperagain.adapter



import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperagain.FinalWallpaper
import com.example.wallpaperagain.Models.add_models
import com.example.wallpaperagain.databinding.ItemCategoriWiseBinding



class latest_adapter(private val context: Context, private var productList: List<add_models>) :
    RecyclerView.Adapter<latest_adapter.ProductViewHolder>() {

    private var productListFiltered: List<add_models> = productList

    inner class ProductViewHolder(private val binding: ItemCategoriWiseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: add_models) {
            binding.apply {

                Glide.with(context)
                    .load(product.images) // Assuming the first image is the main one
                    .into(catWiseImg)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemCategoriWiseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productListFiltered[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, FinalWallpaper::class.java)
            intent.putExtra("link", productList[position].images)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productListFiltered.size
    }

    fun filter(query: String) {
        productListFiltered = productList.filter { product ->
            product.title?.contains(query, ignoreCase = true) == true
        }
        notifyDataSetChanged()
    }
}
