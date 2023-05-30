package com.edgar.interview.screens.popular

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.edgar.interview.AppModule
import com.edgar.interview.databinding.FragmentPopularTvShowsBinding
import com.edgar.interview.network.models.response.TvShows
import com.edgar.interview.screens.popular.bottomsheet.BottomSheetFilterListener
import com.edgar.interview.screens.popular.bottomsheet.BottomSheetTvShowFilterFragment
import com.edgar.interview.screens.tvshowdetails.TvShowDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularTvShowsFragment : Fragment(), PopularTvShowsAdapterListener, BottomSheetFilterListener {

    private val viewModel: PopularTvShowsViewModel by viewModels()
    private lateinit var binding: FragmentPopularTvShowsBinding
    private lateinit var pagingAdapter: TvShowPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularTvShowsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagingAdapter = TvShowPagingAdapter(this)
        binding.rvPopularTvShows.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter = pagingAdapter
            isNestedScrollingEnabled = false
        }

        lifecycleScope.launch {
            viewModel.getPopularTvShows("popularity.desc").collectLatest {
                pagingAdapter.submitData(it)
            }
        }

        binding.btnFilter.setOnClickListener {
            val bottomSheetCommunityTypeFragment = BottomSheetTvShowFilterFragment(this)
            bottomSheetCommunityTypeFragment.show(
                requireActivity().supportFragmentManager,
                BottomSheetTvShowFilterFragment::class.java.simpleName
            )
        }

    }

    override fun updateTvShow(tvShow: TvShows, isChecked: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            AppModule.provideTvShowDatabase(requireContext()).addToFavorites(tvShow.id, isChecked)
        }
    }

    override fun goToTvShowDetails(tvShowId: Double) {
        val intent = Intent(requireContext(), TvShowDetailsActivity::class.java)
        intent.putExtras(Bundle().apply {
            putDouble("tv_show_id", tvShowId)
        })
        requireContext().startActivity(intent)
    }

    override fun setSortQuery(sortBy: String, radioButton: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            AppModule.provideTvShowDatabase(requireContext()).getPopularTvShowsDao().clearAllTvShows()
            AppModule.provideTvShowDatabase(requireContext()).getRemoteKeysDao().clearRemoteKeys()
            viewModel.getPopularTvShows(sortBy).collectLatest {
                pagingAdapter.submitData(it)
            }
        }

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt("radio_button_id", radioButton)
            apply()
        }

    }


}