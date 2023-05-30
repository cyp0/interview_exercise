package com.edgar.interview.screens.tvshowdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.edgar.interview.R
import com.edgar.interview.databinding.FragmentTvShowDetailsBinding
import com.edgar.interview.screens.episodedetails.EpisodeDetailsFragment
import com.edgar.interview.screens.episodedetails.TvShowDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailsFragment : Fragment(), EpisodeAdapterListener {

    private lateinit var binding : FragmentTvShowDetailsBinding

    companion object {
        fun newInstance(args: Bundle?): TvShowDetailsFragment {
            val fragment = TvShowDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: TvShowDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvShowDetailsBinding.inflate(inflater)
        arguments?.let { viewModel.getTvShowDetails(it.getDouble("tv_show_id")) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tvShowDetails.observe(viewLifecycleOwner){

            with(binding){
                Glide.with(ivCoverImage)
                    .load(it.getPoster())
                    .fitCenter()
                    .into(ivCoverImage)

                topAppBar.title = it.name
                tvEpisodeTitle.text = it.seasons.get(0).overview
                tvEpisodeData.text = it.name
                tvDescription.text = it.overview
                if(it.seasons.count() > 1) {
                    spinnerSeasons.adapter = ArrayAdapter(requireContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, it.seasons)
                }else{
                    spinnerSeasons.visibility = View.GONE
                }
            }

        }

        viewModel.seasonDetails.observe(viewLifecycleOwner){
            val adapter = EpisodesAdapter(it.episodes, this)
            with(binding){
                tvEpisodeTitle.text = "Season "+ it.episodes[0].season_number.toString() + " Episode " + it.episodes[0].episode_number + "\n" + it.episodes[0].name
                tvEpisodeData.text = "Aired on " + it.episodes[0].air_date
                tvDescription.text = it.episodes[0].overview
                rvEpisodes.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    this.adapter = adapter
                    isNestedScrollingEnabled = false
                }
            }
        }

        binding.spinnerSeasons.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.tvShowDetails.value?.seasons?.get(p2)?.let { viewModel.getSeasonDetails(it.season_number) }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }


    }

    override fun openEpisodeDetails(tvShowDetails: TvShowDetails) {

        val fragment = EpisodeDetailsFragment.newInstance(
            Bundle().apply {
            putParcelable("tvShow", tvShowDetails.apply { title = viewModel.tvShowDetails.value!!.name })
        })

        requireActivity().getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .addToBackStack("")
            .commit()
    }
}