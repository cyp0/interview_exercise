package com.edgar.interview.screens.popular.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.edgar.interview.R
import com.edgar.interview.databinding.BottomSheetTvShowFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetTvShowFilterFragment(val listener: BottomSheetFilterListener): BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetTvShowFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.bottom_sheet_tv_show_filter,
            null,
            false
        )

        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val highScore = sharedPref.getInt("radio_button_id", 0)

        if(highScore != 0) binding.rgSort.check(highScore)
        else binding.rbPopularityDesc.isChecked = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            binding.rbPopularityDesc.setOnCheckedChangeListener { compoundButton, b ->
                if(b){
                    listener.setSortQuery("popularity.desc", compoundButton.id)
                }
            }

            binding.rbPopularityAsc.setOnCheckedChangeListener { compoundButton, b ->
                if(b){
                    listener.setSortQuery("popularity.asc", compoundButton.id)
                }
            }

            binding.rbVoteCountDesc.setOnCheckedChangeListener { compoundButton, b ->
                if(b){
                    listener.setSortQuery("vote_count.desc", compoundButton.id)
                }
            }

            binding.rbVoteCountAsc.setOnCheckedChangeListener { compoundButton, b ->
                if(b){
                    listener.setSortQuery("vote_count.asc", compoundButton.id)
                }
            }
        }
    }
}