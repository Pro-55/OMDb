package com.example.omdb.ui.details

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.navigation.fragment.navArgs
import com.example.omdb.R
import com.example.omdb.databinding.FragmentFullPosterBinding
import com.example.omdb.framework.BaseFragment
import com.example.omdb.util.extensions.addPosterPlaceholder
import com.example.omdb.util.extensions.diskCacheStrategyAll
import com.example.omdb.util.extensions.getDisplayMetrics
import com.example.omdb.util.extensions.glide
import com.example.omdb.util.listners.OnDragToDismissListener

class FullPosterFragment : BaseFragment() {

    //Global
    private val TAG = FullPosterFragment::class.java.simpleName
    private lateinit var binding: FragmentFullPosterBinding
    private val args by navArgs<FullPosterFragmentArgs>()
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_full_poster, container, false)

        val h = args.height
        val w = args.width
        val constraintSet = ConstraintSet()
        constraintSet.apply {
            clone(binding.constraintsFullPoster)
            setDimensionRatio(R.id.img_poster, "$w:$h")
            applyTo(binding.constraintsFullPoster)
        }

        val url = args.posterUrl
        binding.imgPoster.transitionName = url

        glide.load(url)
            .diskCacheStrategyAll()
            .addPosterPlaceholder(requireContext())
            .into(binding.imgPoster)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnTouchListener(
            OnDragToDismissListener(
                displayMetrics = requireActivity().getDisplayMetrics(),
                orientation = OnDragToDismissListener.Orientation.VERTICAL,
                listener = object : OnDragToDismissListener.Listener {
                    override fun onDismiss() {
                        onBackPressed()
                    }
                })
        )
    }
}