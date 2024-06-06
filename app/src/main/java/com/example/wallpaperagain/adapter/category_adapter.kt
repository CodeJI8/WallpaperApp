package com.example.wallpaperagain.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import cat_model
import com.bumptech.glide.Glide
import com.example.wallpaperagain.MainActivity
import com.example.wallpaperagain.cat_activity


import com.example.wallpaperagain.databinding.ItemCategoryBinding


class category_adapter(private val context: Context, private val list: ArrayList<cat_model>) :
    RecyclerView.Adapter<category_adapter.category_viewholder>() {

    fun updateList(newList: List<cat_model>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class category_viewholder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): category_viewholder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return category_viewholder(binding)
    }

    override fun onBindViewHolder(holder: category_viewholder, position: Int) {

        val imageUrl = list[position].category_image
        Glide.with(context)
            .load(imageUrl)
            .into(holder.binding.catImage)

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, cat_activity::class.java)
            intent.putExtra("cat", list[position].category)
            context.startActivity(intent)
        }

//        holder.itemView.setOnClickListener {
//            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
//
//            val fragment = home_fragment()
//            // Pass data to the fragment if needed
//            val bundle = Bundle()
//            bundle.putString("cat", list[position].category)
//            fragment.arguments = bundle
//
//            // Replace the current fragment with the new one
//            val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.container_frag, fragment)
//            transaction.addToBackStack(null) // Optional: Add the transaction to the back stack
//            transaction.commit()
//        }

    }



    override fun getItemCount() = list.size
}
