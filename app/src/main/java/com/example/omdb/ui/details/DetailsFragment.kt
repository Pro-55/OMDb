package com.example.omdb.ui.details

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.omdb.R
import com.example.omdb.databinding.FragmentDetailsBinding
import com.example.omdb.framework.BaseFragment
import com.example.omdb.models.*
import com.example.omdb.ui.HomeViewModel
import com.example.omdb.util.Constants
import com.example.omdb.util.extensions.glide
import com.example.omdb.util.extensions.showShortSnackBar
import com.example.omdb.util.extensions.visible
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment() {

    //Global
    private val TAG = DetailsFragment::class.java.simpleName
    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel by viewModels<HomeViewModel>()
    private val glide by lazy { glide() }
    private var shortData: ShortData? = null
    private var fullData: FullData? = null
    private var contentId: String? = null
    private var height = 4
    private var width = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shortData = args.shortData
        contentId = args.contentId
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
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        setListeners()

        if (shortData != null) setShortData(shortData!!)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (fullData != null) bindDetails(fullData!!)
        else if (!contentId.isNullOrEmpty()) viewModel.getDetails(contentId!!)
            .observe(viewLifecycleOwner, { bindDetailsResource(it) })
        else {
            requireActivity().showShortSnackBar(Constants.REQUEST_FAILED_MESSAGE)
            onBackPressed()
        }
    }

    private fun setListeners() {
        binding.efabShare.setOnClickListener { showShareIntent() }
    }

    private fun setShortData(data: ShortData) {
        contentId = data._id
        binding.cardPoster.transitionName = data._id
        binding.imgPoster.transitionName = data.poster

        glide.asBitmap().load(data.poster)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(
                AppCompatResources.getDrawable(requireContext(), R.drawable.placeholder_poster)
            )
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

        binding.txtTitle.text = data.title
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

        if (shortData?.poster == null) {
            binding.imgPoster.transitionName = data.poster
            glide.asBitmap().load(data.poster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(
                    AppCompatResources.getDrawable(requireContext(), R.drawable.placeholder_poster)
                )
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

        binding.txtTitle.text = data.title

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
        if (contentId.isNullOrEmpty()) return
        val action = DetailsFragmentDirections.navigateDetailsToEpisodes(contentId!!, season)
        findNavController().navigate(action)
    }

    private fun showShareIntent() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "omdb.example.com/details/${contentId}")
            type = "text/*"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}