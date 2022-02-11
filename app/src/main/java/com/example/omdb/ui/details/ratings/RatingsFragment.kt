package com.example.omdb.ui.details.ratings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdb.R
import com.example.omdb.databinding.FragmentRatingsBinding
import com.example.omdb.framework.BaseFragment
import com.example.omdb.util.extensions.visible

class RatingsFragment : BaseFragment() {

    //Global
    private val TAG = RatingsFragment::class.java.simpleName
    private lateinit var binding: FragmentRatingsBinding
    private val args by navArgs<RatingsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ratings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBtnBack.setOnClickListener { onBackPressed() }

        setupRecyclerview()
    }

    private fun setupRecyclerview() {
        val adapter = RatingsAdapter()

        binding.recyclerReviews.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerReviews.adapter = adapter

        val ratings = args.ratings.asList()

        if (ratings.isNotEmpty()) adapter.swapData(ratings) else binding.txtNotRatings.visible()

    }

}