package com.edgar.interview.screens.episodedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.edgar.interview.R
import com.edgar.interview.databinding.FragmentEpisodeDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetailsFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle): EpisodeDetailsFragment{
            val fragment = EpisodeDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: EpisodeDetailsViewModel by viewModels()
    private lateinit var binding: FragmentEpisodeDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodeDetailsBinding.inflate(inflater)
        val tvShowDetails: TvShowDetails? = arguments?.getParcelable("tvShow")
        tvShowDetails?.let {
            viewModel.findTvShowByTitle(it)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.episodeDetails.observe(viewLifecycleOwner){
            with(binding){
                Glide.with(ivPoster.context)
                    .load(it.Poster).error(ContextCompat.getDrawable(this@EpisodeDetailsFragment.requireContext(), R.drawable.ic_error))
                    .fitCenter()
                    .into(ivPoster)
                it.Title.also {title ->
                    tvTitle.text = title + "\nSeason: " + it.Season + "\nYear: " + it.Year + " Runtime: " + it.Runtime
                }?: kotlin.run {
                    tvTitle.text = "Could not get episode details"
                }

                tvWriter.text = it.Writer
                tvActors.text = it.Actors
            }
        }


    }
}