package com.example.omdb.ui.details.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdb.R
import com.example.omdb.databinding.FragmentEpisodesBinding
import com.example.omdb.framework.BaseFragment
import com.example.omdb.models.*
import com.example.omdb.ui.HomeViewModel
import com.example.omdb.ui.details.DetailsFragment
import com.example.omdb.util.extensions.showShortSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodesFragment : BaseFragment() {

    //Global
    private val TAG = DetailsFragment::class.java.simpleName
    private lateinit var binding: FragmentEpisodesBinding
    private val args by navArgs<EpisodesFragmentArgs>()
    private val viewModel by viewModels<HomeViewModel>()
    private var adapter: EpisodesAdapter? = null
    private val episodes = mutableListOf<Episode>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_episodes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBtnBack.setOnClickListener { onBackPressed() }

        setupRecyclerview()

        if (episodes.isNotEmpty()) adapter?.swapData(episodes)
        else viewModel.getEpisodes(args.id, args.season)
            .observe(viewLifecycleOwner, Observer { bindResource(it) })

    }

    private fun setupRecyclerview() {
        adapter = EpisodesAdapter(args.season)
        adapter?.listener = object : EpisodesAdapter.Listener {
            override fun episodeClicked(episode: Episode) {
                val shortData = ShortData(_id = episode._id, title = episode.title)
                val action = EpisodesFragmentDirections.navigateEpisodesToDetails(shortData)
                findNavController().navigate(action)
            }
        }

        binding.recyclerEpisodes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerEpisodes.adapter = adapter

    }

    private fun bindResource(resource: Resource<Season>) {
        when (resource.status) {
            Status.LOADING -> Unit
            Status.ERROR -> showShortSnackBar(resource.message)
            Status.SUCCESS -> if (!resource.data?.episodes.isNullOrEmpty()) {
                episodes.clear()
                episodes.addAll(resource.data?.episodes!!)
                adapter?.swapData(episodes)
            } else
                showShortSnackBar(resource.message)
        }
    }

}
