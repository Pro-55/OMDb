package com.example.omdb.util.listners

import android.annotation.SuppressLint
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import com.example.omdb.util.extensions.animateTranslate

class OnDragToDismissListener(
    private val displayMetrics: DisplayMetrics,
    private val orientation: Orientation,
    private val listener: Listener
) : View.OnTouchListener {

    // Global
    private var dX = 0F
    private var dY = 0F

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, mE: MotionEvent?): Boolean {
        if (v == null || mE == null) return false
        when (mE.action) {
            MotionEvent.ACTION_DOWN -> {
                when (orientation) {
                    Orientation.HORIZONTAL -> setDX(v, mE)
                    Orientation.VERTICAL -> setDY(v, mE)
                    Orientation.MIXED -> {
                        setDX(v, mE)
                        setDY(v, mE)
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val mEX: Float
                val mEY: Float
                when (orientation) {
                    Orientation.HORIZONTAL -> {
                        mEX = getMEX(mE)
                        mEY = 0F
                    }
                    Orientation.VERTICAL -> {
                        mEX = 0F
                        mEY = getMEY(mE)
                    }
                    Orientation.MIXED -> {
                        mEX = getMEX(mE)
                        mEY = getMEY(mE)
                    }
                }
                v.animateTranslate(tX = mEX, tY = mEY)
            }
            MotionEvent.ACTION_UP -> {
                val shouldDismiss = when (orientation) {
                    Orientation.HORIZONTAL -> isHorizontalOutOfBound(v)
                    Orientation.VERTICAL -> isVerticalOutOfBound(v)
                    Orientation.MIXED -> isVerticalOutOfBound(v) || isHorizontalOutOfBound(v)
                }
                if (shouldDismiss) listener.onDismiss()
                else v.animateTranslate(duration = 150)
            }
        }
        return true
    }

    private fun setDX(v: View, mE: MotionEvent) {
        dX = v.x - mE.rawX
    }

    private fun setDY(v: View, mE: MotionEvent) {
        dY = v.y - mE.rawY
    }

    private fun getMEX(mE: MotionEvent): Float = mE.rawX + dX

    private fun getMEY(mE: MotionEvent): Float = mE.rawY + dY

    private fun isHorizontalOutOfBound(v: View): Boolean {
        val start = v.x
        val end = start + v.right
        val widthBound = displayMetrics.widthPixels / 2
        return start > widthBound || end < widthBound
    }

    private fun isVerticalOutOfBound(v: View): Boolean {
        val top = v.y
        val bottom = top + v.bottom
        val height = displayMetrics.heightPixels
        return top > height / 4 || bottom < height * 3 / 4
    }

    interface Listener {
        fun onDismiss()
    }

    enum class Orientation {
        HORIZONTAL,
        VERTICAL,
        MIXED
    }
}