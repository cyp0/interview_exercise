package com.edgar.interview.screens.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.edgar.interview.databinding.FragmentFavoriteTvShowsBinding
import com.edgar.interview.screens.popular.FavoriteTvShowsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteTvShowsFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteTvShowsFragment()
    }

    private val viewModel: FavoriteTvShowsViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteTvShowsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteTvShowsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteTvShows()

        lifecycleScope.launch {
            viewModel.favoriteTvShows.collectLatest {
                val adapter = FavoriteTvShowsAdapter(it)
                binding.rvPopularTvShows.apply {
                    layoutManager = GridLayoutManager(requireContext(), 2)
                    this.adapter = adapter
                }
            }
        }
    }
}