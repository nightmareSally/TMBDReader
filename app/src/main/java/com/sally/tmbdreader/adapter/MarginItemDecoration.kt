package com.sally.tmbdreader.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sally.tmbdreader.util.Extensions.dpToPx

class MarginItemDecoration(private val margin: Float) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        if (position != 0) {
            outRect.top = margin.dpToPx().toInt()
        }
    }
}