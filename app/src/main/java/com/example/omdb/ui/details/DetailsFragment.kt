package com.example.omdb.ui.details

import android.graphics.Bitmap
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentDetailsBinding
import com.example.omdb.models.*
import com.example.omdb.ui.HomeViewModel
import com.example.omdb.util.extensions.getViewModel
import com.example.omdb.util.extensions.glide
import com.example.omdb.util.extensions.showShortSnackBar
import com.example.omdb.util.extensions.visible
import com.google.android.material.chip.Chip
import javax.inject.Inject

class DetailsFragment : BaseFragment() {

    companion object {
        private val TAG = DetailsFragment::class.java.simpleName
    }

    //Global
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel by lazy { requireActivity().getViewModel<HomeViewModel>(factory) }
    private val glide by lazy { glide() }
    private var fullData: FullData? = null
    private var height = 4
    private var width = 3

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        val shortData = args.shortData
        binding.cardPoster.transitionName = shortData._id
        binding.imgPoster.transitionName = shortData.poster

        glide.asBitmap().load(shortData.poster)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(resources.getDrawable(R.drawable.placeholder_poster))
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean = false

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    val bitmapHeight = resource?.height ?: 0
                    val bitmapWidth = resource?.width ?: 0
                    if (bitmapHeight > 0) height = bitmapHeight
                    if (bitmapWidth > 0) width = bitmapWidth
                    return false
                }
            })
            .into(binding.imgPoster)

        binding.txtTitle.text = shortData.title

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (fullData != null) bindDetails(fullData!!)
        else viewModel.getDetails(args.shortData._id)
            .observe(viewLifecycleOwner, Observer { bindDetailsResource(it) })
    }

    private fun bindDetailsResource(resource: Resource<FullData>) {
        when (resource.status) {
            Status.LOADING -> Unit
            Status.ERROR -> showShortSnackBar(resource.message)
            Status.SUCCESS -> if (resource.data != null) bindDetails(resource.data)
            else showShortSnackBar(resource.message)
        }
    }

    private fun bindDetails(data: FullData) {

        fullData = data

        if (args.shortData.poster == null) {
            binding.imgPoster.transitionName = data.poster
            glide.asBitmap().load(data.poster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(resources.getDrawable(R.drawable.placeholder_poster))
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean = false

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        val bitmapHeight = resource?.height ?: 0
                        val bitmapWidth = resource?.width ?: 0
                        if (bitmapHeight > 0) height = bitmapHeight
                        if (bitmapWidth > 0) width = bitmapWidth
                        return false
                    }
                })
                .into(binding.imgPoster)
        }

        binding.txtYear.text = "(${data.year})"

        binding.dividerYearRated.text = resources.getString(R.string.divider_bullet)

        binding.txtRated.text = data.rated

        if (data.contentType != Type.SERIES) {

            binding.dividerRatedRunTime.text = resources.getString(R.string.divider_bullet)

            binding.txtRunTime.text = data.runtime
        }

        binding.txtGenre.text = data.genre

        binding.barRating.rating = data.rating / 2

        binding.txtPlot.text = data.plot

        binding.cardPoster.setOnClickListener {
            val action =
                DetailsFragmentDirections.navigateDetailsToFullPoster(data.poster, height, width)
            val extras =
                FragmentNavigatorExtras(binding.imgPoster to binding.imgPoster.transitionName)
            findNavController().navigate(action, extras)
        }

        binding.txtBtnRatings.setOnClickListener {
            val ratings = mutableListOf<Rating>()
                .apply { addAll(data.ratings) }
                .toTypedArray()
            val action = DetailsFragmentDirections.navigateDetailsToRatings(ratings)
            findNavController().navigate(action)
        }

        val seasons = try {
            data.seasons?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
        if (seasons > 0) {
            binding.txtTitleSeasons.visible()
            setupSeasonChips(seasons)
        }

        binding.txtBtnTeamDetails.setOnClickListener {
            val teamDetails = TeamDetails(
                cast = data.actors,
                crew = data.writer,
                director = data.director,
                production = data.production
            )
            val action = DetailsFragmentDirections.navigateDetailsToTeamDetails(teamDetails)
            findNavController().navigate(action)
        }
    }

    private fun setupSeasonChips(seasons: Int) {
        (1..seasons).forEach { season ->
            val chip = Chip(requireContext()).apply {
                setTextAppearance(requireContext(), R.style.ChipTextStyle)
                text = "Season $season"
                tag = season
                setOnClickListener { showEpisodes(season) }
            }
            binding.chipGroupSeasons.addView(chip)
        }
    }

    private fun showEpisodes(season: Int) {
        val action = DetailsFragmentDirections.navigateDetailsToEpisodes(args.shortData._id, season)
        findNavController().navigate(action)
    }

}