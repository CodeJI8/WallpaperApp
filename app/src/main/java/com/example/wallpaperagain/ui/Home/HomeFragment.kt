package com.example.wallpaperagain.ui.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wallpaperagain.databinding.FragmentHomeBinding
import com.example.wallpaperagain.ui.Tabs.AbstractFragment
import com.example.wallpaperagain.ui.Tabs.AnimalFragment
import com.example.wallpaperagain.ui.Tabs.GamingFragment
import com.example.wallpaperagain.ui.Tabs.MainFragment
import com.example.wallpaperagain.ui.Tabs.NatureFragment
import com.example.wallpaperagain.ui.Tabs.Popular_Fragment
import com.example.wallpaperagain.ui.Tabs.SpaceFragment


class HomeFragment : Fragment() {
private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(MainFragment(), "Home")
        adapter.addFragment(Popular_Fragment(), "Popular")
        adapter.addFragment(AnimalFragment(), "Animal")
        adapter.addFragment(NatureFragment(), "Nature")
        adapter.addFragment(SpaceFragment(), "Space")
        adapter.addFragment(GamingFragment(), "Gaming")
        adapter.addFragment(AbstractFragment(), "Abstract")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        return binding.root





    }





}