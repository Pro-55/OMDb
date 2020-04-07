package com.example.omdb.ui.details

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentSeasonsBinding

class SeasonsFragment : BaseFragment() {

    companion object {
        private val TAG = DetailsFragment::class.java.simpleName
    }

    //Global
    private lateinit var binding: FragmentSeasonsBinding
    private val args by navArgs<SeasonsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Slide(Gravity.END)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_seasons, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerview()
    }

    private fun setupRecyclerview() {
        val adapter = SeasonsAdapter()
        adapter.listener = object : SeasonsAdapter.Listener {
            override fun seasonClicked(season: Int) {

            }
        }

        binding.recyclerSeasons.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSeasons.adapter = adapter

        val list = mutableListOf<Int>()
        if (args.seasons > 0) for (index in 1..args.seasons + 1) list.add(index)
        adapter.swapData(list)
    }
}
