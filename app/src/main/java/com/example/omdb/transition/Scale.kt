package com.example.omdb.transition

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.transition.Transition
import android.transition.TransitionValues
import android.transition.Visibility
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.example.omdb.util.TransitionUtil

class Scale : Visibility {

    companion object {
        private const val SCALE_X = "scale:scaleX"
        private const val SCALE_Y = "scale:scaleY"
    }

    enum class Direction {
        DOWN,
        RIGHT,
        UP,
        LEFT,
        CUSTOM
    }

    private val maxScale = 1F
    private var minScale = 0F
    private var pivotX = 0F
    private var pivotY = 0F
    private var direction: Direction? = null

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)

    constructor(
        minScale: Float = 0F,
        direction: Direction? = null,
        pivotX: Float = 0F,
        pivotY: Float = 0F
    ) {
        setValues(minScale, direction, pivotX, pivotY)
    }

    private fun setValues(minScale: Float, direction: Direction?, pivotX: Float, pivotY: Float) {
        require(minScale >= 0F) { "Min Scale can not be less than Zero" }
        this.minScale = minScale
        this.direction = direction
        this.pivotX = pivotX
        this.pivotY = pivotY
    }

    private fun createAnimation(
        view: View,
        startScale: Float,
        endScale: Float,
        values: TransitionValues?
    ): Animator? {
        val initialScaleX = view.scaleX
        val initialScaleY = view.scaleY

        var startScaleX: Float
        var startScaleY: Float
        when (direction) {
            Direction.DOWN, Direction.UP -> {
                startScaleX = initialScaleX
                startScaleY = initialScaleY * startScale
            }
            Direction.RIGHT, Direction.LEFT -> {
                startScaleX = initialScaleX * startScale
                startScaleY = initialScaleY
            }
            else -> {
                startScaleX = initialScaleX * startScale
                startScaleY = initialScaleY * startScale
            }
        }

        val endScaleX = initialScaleX * endScale
        val endScaleY = initialScaleY * endScale

        if (values != null) {
            val savedScaleX = values.values[SCALE_X] as? Float
            val savedScaleY = values.values[SCALE_Y] as? Float

            if (savedScaleX != null && savedScaleX != initialScaleX) startScaleX = savedScaleX

            if (savedScaleY != null && savedScaleY != initialScaleY) startScaleY = savedScaleY

        }

        view.scaleX = startScaleX
        view.scaleY = startScaleY

        when (direction) {
            Direction.DOWN -> view.pivotY = pivotY
            Direction.RIGHT -> view.pivotX = pivotX
            Direction.UP -> view.pivotY = view.measuredHeight.toFloat()
            Direction.LEFT -> view.pivotX = view.measuredWidth.toFloat()
            Direction.CUSTOM -> {
                view.pivotX = pivotX
                view.pivotY = pivotY
            }
        }

        val animator = TransitionUtil.mergeAnimators(
            ObjectAnimator.ofFloat(view, View.SCALE_X, startScaleX, endScaleX),
            ObjectAnimator.ofFloat(view, View.SCALE_Y, startScaleY, endScaleY)
        )
        addListener(object : TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                view.scaleX = initialScaleX
                view.scaleY = initialScaleY
                transition?.removeListener(this)
            }

            override fun onTransitionResume(transition: Transition?) {}

            override fun onTransitionPause(transition: Transition?) {}

            override fun onTransitionCancel(transition: Transition?) {}

            override fun onTransitionStart(transition: Transition?) {}
        })
        return animator
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        super.captureStartValues(transitionValues)
        transitionValues.values[SCALE_X] = transitionValues.view.scaleX
        transitionValues.values[SCALE_Y] = transitionValues.view.scaleY
    }

    override fun onAppear(
        sceneRoot: ViewGroup,
        view: View,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? = createAnimation(view, minScale, maxScale, startValues)

    override fun onDisappear(
        sceneRoot: ViewGroup,
        view: View,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? = createAnimation(view, maxScale, minScale, startValues)

}