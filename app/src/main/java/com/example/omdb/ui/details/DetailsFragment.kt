package com.example.omdb.ui.details

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentDetailsBinding
import com.example.omdb.models.FullData
import com.example.omdb.models.Resource
import com.example.omdb.models.Status
import com.example.omdb.ui.HomeViewModel
import com.example.omdb.util.extensions.getViewModel
import com.example.omdb.util.extensions.glide
import com.example.omdb.util.extensions.showShortSnackBar
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

        glide.load(shortData.poster)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(resources.getDrawable(R.drawable.placeholder_poster))
            .into(binding.imgPoster)

        binding.txtTitle.text = shortData.title

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shortData = args.shortData

        viewModel.getDetails(shortData._id)
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
        Log.d(TAG, "TestLog: d:$data")
    }
}