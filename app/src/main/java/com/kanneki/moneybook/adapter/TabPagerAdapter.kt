package com.kanneki.moneybook.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.kanneki.moneybook.model.TitleFragmentModule

class TabPagerAdapter(private val list: List<TitleFragmentModule>): PagerAdapter() {

    override fun getCount(): Int = list.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = (`object` === view)

    override fun getPageTitle(position: Int): CharSequence? = list[position].title

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}