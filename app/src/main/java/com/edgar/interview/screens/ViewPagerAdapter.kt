package com.edgar.interview.screens

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.edgar.interview.screens.favorites.FavoriteTvShowsFragment
import com.edgar.interview.screens.popular.PopularTvShowsFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return PopularTvShowsFragment()
            1 -> return FavoriteTvShowsFragment()
        }
        return PopularTvShowsFragment()
    }
}