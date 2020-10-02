package com.example.omdb.ui.search

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.omdb.R
import com.example.omdb.databinding.FragmentSearchBinding
import com.example.omdb.framework.BaseFragment
import com.example.omdb.models.*
import com.example.omdb.ui.HomeViewModel
import com.example.omdb.util.extensions.*
import com.example.omdb.views.CustomGridLayoutManager
import com.jakewharton.rxbinding.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    //Global
    private val TAG = SearchFragment::class.java.simpleName
    private lateinit var binding: FragmentSearchBinding
    private val args by navArgs<SearchFragmentArgs>()
    private val viewModel by viewModels<HomeViewModel>()
    private val glide by lazy { glide() }
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

        setupRecyclerView()

        setupObserver()

        setupSearch()

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

    }

    private fun setupRecyclerView() {
        val layoutManager = CustomGridLayoutManager(requireContext(), 2, VERTICAL, false)
        adapter = SearchAdapter(glide)
        adapter?.listener = object : SearchAdapter.Listener {
            override fun onClick(data: ShortData, sharedCard: View, sharedImage: View) {
                hideKeyboard()
                clearFocus()
                val action = SearchFragmentDirections.navigateSearchToDetails(data)
                val extras = FragmentNavigatorExtras(
                    sharedCard to sharedCard.transitionName,
                    sharedImage to sharedImage.transitionName
                )
                findNavController().navigate(action, extras)
            }

            override fun onHold(data: ShortData) {
                layoutManager.setScrollEnabled(false)
                hideKeyboard()
                showPeekView(data)
            }

            override fun onRelease() {
                layoutManager.setScrollEnabled(true)
                hidePeekView()
            }
        }

        binding.recyclerSearch.layoutManager = layoutManager
        binding.recyclerSearch.adapter = adapter
        binding.recyclerSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                hideKeyboard()
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
                    if (searchText.isNotEmpty()) fetchData(searchText)
                }
            })

        binding.editSearch.setOnEditorActionListener { _, _, _ ->
            clearFocus()
            hideKeyboard()
            val searchText = binding.editSearch.text?.toString()?.trim()
            if (!searchText.isNullOrEmpty()) fetchData(searchText)
            true
        }
    }

    private fun setupObserver() {
        val source = when (args.category) {
            Type.MOVIES -> viewModel.movieSearch
            Type.SERIES -> viewModel.seriesSearch
            else -> null
        }
        source?.observe(viewLifecycleOwner, Observer { bindResource(it) })
    }

    private fun fetchData(searText: String, size: Int = 0) {
        currentText = searText
        when (args.category) {
            Type.MOVIES -> viewModel.searchMovies(searText, size)
            Type.SERIES -> viewModel.searchSeries(searText, size)
        }
    }

    private fun bindResource(resource: Resource<SearchResult>) {
        when (resource.status) {
            Status.LOADING -> isLoading = true
            Status.ERROR -> {
                isLoading = false
                showShortToast(resource.message)
            }
            Status.SUCCESS -> bindSearchResult(resource.data)
        }
    }

    private fun bindSearchResult(result: SearchResult?) {
        isLoading = false
        totalCount = result?.totalResults?.toInt() ?: 0
        val list = result?.search ?: listOf()
        if (list.isNotEmpty()) hidePlaceholders() else showPlaceholders()
        adapter?.swapData(list)
    }

    private fun showPlaceholders() {
        binding.recyclerSearch.gone()
        binding.imgIcon.visible()
        binding.txtTitle.visible()
    }

    private fun hidePlaceholders() {
        binding.imgIcon.gone()
        binding.txtTitle.gone()
        binding.recyclerSearch.visible()
    }

    private fun showPeekView(data: ShortData) {
        binding.layoutBlur.apply { visibleWithFade(parent as ViewGroup) }
        binding.cardPeekPoster.apply { visibleWithScaleFade(parent as ViewGroup) }

        glide.load(data.poster)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(resources.getDrawable(R.drawable.placeholder_poster))
            .into(binding.imgPeekPoster)

    }

    private fun hidePeekView() {
        binding.cardPeekPoster.apply { goneWithScaleFade(parent as ViewGroup) }
        binding.layoutBlur.apply { goneWithFade(parent as ViewGroup) }
    }

    private fun clearFocus() {
        requireActivity().currentFocus?.clearFocus()
    }

    private fun hideKeyboard() {
        requireActivity().hideKeyboard()
    }

    override fun onBackPressed() {
        viewModel.clearSearchData(args.category)
        super.onBackPressed()
    }

    override fun onDestroy() {
        editSubscription?.unsubscribe()
        super.onDestroy()
    }
}