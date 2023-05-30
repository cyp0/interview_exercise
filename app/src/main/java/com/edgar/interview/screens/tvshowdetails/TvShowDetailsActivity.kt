package com.edgar.interview.screens.tvshowdetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.edgar.interview.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_details)
        loadAndAddToBackStackFragment(TvShowDetailsFragment.newInstance(intent.extras))
    }

    private fun loadAndAddToBackStackFragment(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}