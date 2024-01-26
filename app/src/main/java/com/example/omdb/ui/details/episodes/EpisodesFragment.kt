package com.example.omdb.ui.details.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdb.R
import com.example.omdb.databinding.FragmentEpisodesBinding
import com.example.omdb.domain.model.Episode
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.framework.BaseFragment
import com.example.omdb.util.extensions.showShortSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodesFragment : BaseFragment() {

    //Global
    private val TAG = EpisodesFragment::class.java.simpleName
    private lateinit var binding: FragmentEpisodesBinding
    private val args by navArgs<EpisodesFragmentArgs>()
    private val viewModel by viewModels<EpisodesViewModel>()
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

        setObservers()
    }

    private fun setupRecyclerview() {
        adapter = EpisodesAdapter(args.season)
        adapter?.listener = object : EpisodesAdapter.Listener {
            override fun episodeClicked(episode: Episode) {
                val shortContent = ShortContent(
                    _id = episode._id,
                    title = episode.title,
                    year = episode.released,
                    poster = null
                )
                val action = EpisodesFragmentDirections.navigateEpisodesToDetails(shortContent)
                findNavController().navigate(action)
            }
        }

        binding.recyclerEpisodes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerEpisodes.adapter = adapter
    }

    private fun setObservers() {
        viewModel.season.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> Unit
                is Resource.Error -> showShortSnackBar(it.msg)
                is Resource.Success -> if (!it.data?.episodes.isNullOrEmpty()) {
                    episodes.clear()
                    episodes.addAll(it.data?.episodes!!)
                    adapter?.swapData(episodes)
                } else showShortSnackBar(null)
            }
        }
    }
}