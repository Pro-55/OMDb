package com.example.omdb.util.listners

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import com.example.omdb.util.extensions.animateScale

class OnHoldListener(
    private val listener: Listener,
    private val shouldAnimateView: Boolean = true
) : View.OnTouchListener {

    // Global
    private var isPeeking = false
    private var shouldPeek = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, mE: MotionEvent?): Boolean {
        if (v == null || mE == null) return false
        when (mE.action) {
            MotionEvent.ACTION_DOWN -> {
                shouldPeek = true
                v.postDelayed({
                    if (shouldPeek && !isPeeking) {
                        isPeeking = true
                        listener.onHold()
                    }
                }, 500)
                if (shouldAnimateView) v.animateScale(sX = 0.95F, sY = 0.95F)
            }
            MotionEvent.ACTION_CANCEL -> {
                shouldPeek = false
                if (shouldAnimateView) v.animateScale()
            }
            MotionEvent.ACTION_UP -> {
                shouldPeek = false
                if (shouldAnimateView) v.animateScale()
                if (isPeeking) {
                    isPeeking = false
                    listener.onRelease()
                } else listener.onClick()
            }
        }
        return true
    }

    interface Listener {
        fun onClick()
        fun onHold()
        fun onRelease()
    }

}