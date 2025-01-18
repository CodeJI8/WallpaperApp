package com.example.wallpaperagain.ui.Tabs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperagain.Models.Wallpaper_model
import com.example.wallpaperagain.R
import com.example.wallpaperagain.adapter.PhotoAdapter
import com.example.wallpaperagain.databinding.FragmentAbstractBinding
import com.example.wallpaperagain.databinding.FragmentPopularBinding
import com.example.wallpaperagain.databinding.FragmentSpaceBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL


class AbstractFragment : Fragment() {


    private lateinit var binding: FragmentAbstractBinding
    private lateinit var adapter: PhotoAdapter
    private val apiKey = "ADROWw7lJsQljdgJFjA7Bi9Dmvc5pkLCmegvJdkGMfMvCN7xLGSrj1gp" // Pexels API Key
    private val queryTags = listOf( "Abstract") // Tags for wallpapers
    private val perPage = 20 // Number of items per page
    private var currentPage = 1 // Pagination tracker
    private val wallpapers = mutableListOf<Wallpaper_model>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAbstractBinding.inflate(layoutInflater)

        setupRecyclerView()
        fetchPhotos()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = PhotoAdapter(requireContext(), wallpapers)
        binding.mainRV.layoutManager = GridLayoutManager(context, 2)
        binding.mainRV.adapter = adapter
    }

    private fun fetchPhotos() {
        lifecycleScope.launch {
            val query = queryTags.joinToString(" ")
            val url = "https://api.pexels.com/v1/search?query=$query&per_page=$perPage&page=$currentPage"

            try {
                val response = withContext(Dispatchers.IO) {
                    makeNetworkRequest(url)
                }

                val newWallpapers = parseWallpapers(response)
                updateWallpapers(newWallpapers)

            } catch (e: Exception) {
                Log.e("MainFragment", "Error fetching photos: ${e.message}", e)
            }
        }
    }

    private fun makeNetworkRequest(url: String): String {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.setRequestProperty("Authorization", apiKey)
        connection.requestMethod = "GET"

        return connection.inputStream.bufferedReader().use { it.readText() }
    }

    private fun parseWallpapers(response: String): List<Wallpaper_model> {
        val wallpapers = mutableListOf<Wallpaper_model>()
        val jsonResponse = JSONObject(response)
        val photos = jsonResponse.getJSONArray("photos")

        for (i in 0 until photos.length()) {
            val photo = photos.getJSONObject(i)
            val imageUrl = photo.getJSONObject("src").getString("large")
            wallpapers.add(Wallpaper_model(imageUrl))
        }
        return wallpapers
    }

    private fun updateWallpapers(newWallpapers: List<Wallpaper_model>) {
        wallpapers.addAll(newWallpapers)
        adapter.notifyDataSetChanged()

        // Check if more pages should be loaded
        if (newWallpapers.size == perPage) {
            currentPage++
            fetchPhotos()
        }
    }


}