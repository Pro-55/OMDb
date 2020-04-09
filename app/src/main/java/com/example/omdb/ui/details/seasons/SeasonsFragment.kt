package com.example.omdb.ui.details.seasons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentSeasonsBinding
import com.example.omdb.ui.details.DetailsFragment

class SeasonsFragment : BaseFragment() {

    companion object {
        private val TAG = DetailsFragment::class.java.simpleName
    }

    //Global
    private lateinit var binding: FragmentSeasonsBinding
    private val args by navArgs<SeasonsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_seasons, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBtnBack.setOnClickListener { onBackPressed() }

        setupRecyclerview()
    }

    private fun setupRecyclerview() {
        val adapter = SeasonsAdapter()
        adapter.listener = object : SeasonsAdapter.Listener {
            override fun seasonClicked(season: Int) {
                val action = SeasonsFragmentDirections.navigateSeasonsToEpisodes(args.id, season)
                findNavController().navigate(action)
            }
        }

        binding.recyclerSeasons.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSeasons.adapter = adapter

        val list = (1..args.seasons).toList()
        adapter.swapData(list)
    }
}
