package com.example.omdb.ui.details

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentFullPosterBinding
import com.example.omdb.util.extensions.animateTranslate
import com.example.omdb.util.extensions.getDisplayMetrics
import com.example.omdb.util.extensions.glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FullPosterFragment : BaseFragment() {

    companion object {
        private val TAG = FullPosterFragment::class.java.simpleName
    }

    //Global
    private lateinit var binding: FragmentFullPosterBinding
    private val args by navArgs<FullPosterFragmentArgs>()
    private val h by lazy { requireActivity().getDisplayMetrics().heightPixels }
    private val w by lazy { requireActivity().getDisplayMetrics().widthPixels }
    private val glide by lazy { glide() }
    private var dX = 0F
    private var dY = 0F
    private var job: Job? = null

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
    ): View? {
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
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(resources.getDrawable(R.drawable.placeholder_poster))
            .into(binding.imgPoster)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnTouchListener { v, mE ->
            when (mE.action) {
                MotionEvent.ACTION_DOWN -> {
                    job?.cancel()
                    dX = v.x - mE.rawX
                    dY = v.y - mE.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    val mEX = mE.rawX + dX
                    val mEY = mE.rawY + dY
                    v.animateTranslate(mEX, mEY)
                }
                MotionEvent.ACTION_UP -> {
                    val left = v.x
                    val top = v.y
                    val right = left + v.right
                    val bottom = top + v.bottom
                    v.animateTranslate(duration = 150)
                    job = lifecycleScope.launchWhenStarted {
                        withContext(Dispatchers.IO) {
                            delay(150)
                            val shouldDismiss =
                                left > w / 4 || right < w * 3 / 4 || top > h / 4 || bottom < h * 3 / 4
                            if (shouldDismiss) onBackPressed()
                        }
                    }
                }
            }
            false
        }
    }
}
