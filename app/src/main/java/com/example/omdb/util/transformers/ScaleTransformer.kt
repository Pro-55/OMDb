package com.example.omdb.util.transformers

import android.view.View
import com.yarolegovich.discretescrollview.transform.DiscreteScrollItemTransformer
import com.yarolegovich.discretescrollview.transform.Pivot
import kotlin.math.abs

class ScaleTransformer(private val minScale: Float = 0.5F, private val maxScale: Float = 1F) :
    DiscreteScrollItemTransformer {

    private var pivotX = Pivot.X.CENTER.create()
    private var pivotY = Pivot.Y.CENTER.create()
    private var scaleDiff =
        if (minScale >= 0 && maxScale > 0) maxScale - minScale else throw IllegalArgumentException("minScale & maxScale must be >= 0")

    override fun transformItem(item: View?, position: Float) {
        pivotX.setOn(item)
        pivotY.setOn(item)
        val closenessToCenter = 1F - abs(position)
        val scale = minScale + scaleDiff * closenessToCenter
        item?.scaleX = scale
        item?.scaleY = scale
    }


}