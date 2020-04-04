package com.example.omdb.views

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomGridLayoutManager(
    context: Context,
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int,
    reverseLayout: Boolean
) : GridLayoutManager(context, spanCount, orientation, reverseLayout) {

    private var isScrollEnabled = true

    fun setScrollEnabled(isScrollEnabled: Boolean) {
        this.isScrollEnabled = isScrollEnabled
    }

    override fun canScrollVertically(): Boolean {
        return if (orientation == VERTICAL) isScrollEnabled else false
    }

    override fun canScrollHorizontally(): Boolean {
        return if (orientation == HORIZONTAL) isScrollEnabled else false
    }

}