package com.kanneki.moneybook.utils

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoratorLocal(
        private val mDrawable: Drawable?,
        private val isShowLast: Boolean = false): RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight
        val childCount = if (isShowLast){
            parent.childCount - 1
        } else {
            parent.childCount
        }

        for (i in 0 until childCount){

            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val divderTop = child.bottom + params.bottomMargin
            val dividerBottom = divderTop + (mDrawable?.intrinsicHeight ?: 0)

            mDrawable?.setBounds(dividerLeft, divderTop, dividerRight, dividerBottom)
            mDrawable?.draw(c)
        }

    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mDrawable?.intrinsicHeight ?: 0
    }
}