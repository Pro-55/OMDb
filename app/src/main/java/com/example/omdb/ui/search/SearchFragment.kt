package com.example.omdb.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout.VERTICAL
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentSearchBinding
import com.example.omdb.models.Resource
import com.example.omdb.models.SearchResult
import com.example.omdb.models.Status
import com.example.omdb.models.Type
import com.example.omdb.ui.HomeViewModel
import com.example.omdb.util.extensions.getViewModel
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    companion object {
        private val TAG = SearchFragment::class.java.simpleName
    }

    //Global
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: FragmentSearchBinding
    private val args by navArgs<SearchFragmentArgs>()
    private val viewModel by lazy { requireActivity().getViewModel<HomeViewModel>(factory) }
    private var adapter: SearchAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        val category = args.category.toString()
        val search = resources.getString(R.string.hint_search)

        binding.editSearch.hint = "$search $category"

        val icon: Int
        val color: Int
        when (args.category) {
            Type.MOVIES -> {
                icon = R.drawable.ic_movies
                color = R.color.color_category_movie
            }
            Type.SERIES -> {
                icon = R.drawable.ic_series
                color = R.color.color_category_series
            }
            Type.EPISODES -> {
                icon = R.drawable.ic_episodes
                color = R.color.color_category_episodes
            }
        }
        binding.imgIcon.setImageDrawable(resources.getDrawable(icon))

        binding.txtTitle.apply {
            text = category
            setTextColor(resources.getColor(color))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val source = when (args.category) {
            Type.MOVIES -> viewModel.searchMovies("bat", 1)
            Type.SERIES -> viewModel.searchSeries("bat", 1)
            Type.EPISODES -> viewModel.searchEpisodes("bat", 1)
        }

        source.observe(viewLifecycleOwner, Observer { bindData(it) })

    }

    private fun setupRecyclerView() {
        adapter = SearchAdapter()

        binding.recyclerSearch.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        binding.recyclerSearch.adapter = adapter

    }

    private fun bindData(resource: Resource<SearchResult>) {
        when (resource.status) {
            Status.LOADING -> Unit
            Status.ERROR -> Log.d(TAG, "TestLog: e:${resource.message}")
            Status.SUCCESS -> if (resource.data != null) adapter?.swapData(resource.data.search)
        }
    }

}