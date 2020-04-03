package com.example.omdb.ui.search

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentSearchBinding
import com.example.omdb.models.*
import com.example.omdb.ui.HomeViewModel
import com.example.omdb.util.extensions.*
import com.jakewharton.rxbinding.widget.RxTextView
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
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
    private var isLoading = false
    private var currentText = ""
    private var totalCount = 0
    private var adapter: SearchAdapter? = null
    private var editSubscription: Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionSet().apply {
            addTransition(ChangeTransform())
            addTransition(ChangeBounds())
            interpolator = LinearOutSlowInInterpolator()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        val hint: Int
        val icon: Int
        val sharedContainer: Int
        val sharedIcon: Int
        val sharedTitle: Int
        when (args.category) {
            Type.MOVIES -> {
                hint = R.string.label_movies
                icon = R.drawable.ic_movies
                sharedContainer = R.string.shared_container_movies
                sharedIcon = R.string.shared_icon_movies
                sharedTitle = R.string.shared_title_movies
            }
            Type.SERIES -> {
                hint = R.string.label_series
                icon = R.drawable.ic_series
                sharedContainer = R.string.shared_container_series
                sharedIcon = R.string.shared_icon_series
                sharedTitle = R.string.shared_title_series
            }
            Type.EPISODES -> {
                hint = R.string.label_episodes
                icon = R.drawable.ic_episodes
                sharedContainer = R.string.shared_container_episodes
                sharedIcon = R.string.shared_icon_episodes
                sharedTitle = R.string.shared_title_episodes
            }
        }

        val search = resources.getString(R.string.hint_search)
        val title = resources.getString(hint)

        binding.layoutSearchConstraints.transitionName = resources.getString(sharedContainer)

        binding.editSearch.hint = "$search $title"

        binding.imgIcon.apply {
            transitionName = resources.getString(sharedIcon)
            setImageDrawable(resources.getDrawable(icon))
        }

        binding.txtTitle.apply {
            transitionName = resources.getString(sharedTitle)
            text = title
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSharedElementCallback()

        setupRecyclerView()

        setupSearch()

        setupObserver()

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

    }

    private fun setupRecyclerView() {
        adapter = SearchAdapter(glide())
        adapter?.listener = object : SearchAdapter.Listener {
            override fun onClick(position: Int, data: ShortData, sharedView: View) {
                viewModel.searchPosition = position
                hideKeyboard()
                val action = SearchFragmentDirections.navigateSearchToDetails(data)
                val extras = FragmentNavigatorExtras(sharedView to sharedView.transitionName)
                findNavController().navigate(action, extras)
            }
        }

        binding.recyclerSearch.layoutManager =
            GridLayoutManager(requireContext(), 2, VERTICAL, false)
        binding.recyclerSearch.adapter = adapter
        binding.recyclerSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val size = adapter?.itemCount ?: 0
                if (!isLoading
                    && size < totalCount
                    && !recyclerView.canScrollVertically(1) // check if scrolled to bottom
                ) {
                    fetchData(currentText, size)
                }
            }
        })

    }

    private fun setupSearch() {
        editSubscription = RxTextView.textChanges(binding.editSearch)
            .skip(1)
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<CharSequence>() {
                override fun onCompleted() {}

                override fun onError(e: Throwable) {
                    Log.d(TAG, "TestLog: ${e.message}")
                }

                override fun onNext(searchBoxText: CharSequence) {
                    val searchText = searchBoxText.toString().trim()
                    if (searchText.isNotEmpty() && !isLoading) fetchData(searchText)
                }
            })
    }

    private fun setupObserver() {
        val source = when (args.category) {
            Type.MOVIES -> viewModel.movieSearch
            Type.SERIES -> viewModel.seriesSearch
            Type.EPISODES -> viewModel.episodeSearch
        }
        source.observe(viewLifecycleOwner, Observer { bindResource(it) })
    }

    private fun fetchData(searText: String, size: Int = 0) {
        currentText = searText
        when (args.category) {
            Type.MOVIES -> viewModel.searchMovies(searText, size)
            Type.SERIES -> viewModel.searchSeries(searText, size)
            Type.EPISODES -> viewModel.searchEpisodes(searText, size)
        }
    }

    private fun bindResource(resource: Resource<SearchResult>) {
        when (resource.status) {
            Status.LOADING -> isLoading = true
            Status.ERROR -> isLoading = false
            Status.SUCCESS -> bindSearchResult(resource.data)
        }
    }

    private fun bindSearchResult(result: SearchResult?) {
        isLoading = false
        totalCount = result?.totalResults?.toInt() ?: 0
        val list = result?.search ?: listOf()
        if (list.isNotEmpty()) hidePlaceholders()
        else showPlaceholders()
        adapter?.swapData(list)
    }

    private fun showPlaceholders() {
        binding.imgIcon.visible()
        binding.txtTitle.visible()
    }

    private fun hidePlaceholders() {
        binding.imgIcon.gone()
        binding.txtTitle.gone()
    }

    private fun hideKeyboard() {
        requireActivity().currentFocus?.clearFocus()
        requireActivity().hideKeyboard()
    }

    private fun setSharedElementCallback() {
        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                if (names.isNullOrEmpty() || sharedElements == null) return

                val name = names[0]
                val position = viewModel.searchPosition

                if (position > -1) sharedElements[name] = binding.recyclerSearch
                    .findViewHolderForAdapterPosition(position)?.itemView ?: return

            }
        })
    }

    override fun onBackPressed() {
        viewModel.searchPosition = -1
        viewModel.clearSearchData(args.category)
        super.onBackPressed()
    }

    override fun onDestroy() {
        editSubscription?.unsubscribe()
        super.onDestroy()
    }
}