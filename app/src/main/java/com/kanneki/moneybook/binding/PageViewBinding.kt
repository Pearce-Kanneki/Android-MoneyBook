package com.kanneki.moneybook.binding

import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.kanneki.moneybook.adapter.ViewPageAdapter

@BindingAdapter("isUser")
fun isUserInputEnabled(view: ViewPager2, isTrue: Boolean){
    view.isUserInputEnabled = isTrue
}

@BindingAdapter("adapterSettingList")
fun bindAdapterSetList(view: ViewPager2, list: List<Fragment>?){
    list?.let {
        (view.adapter as? ViewPageAdapter)?.addFragments(it)
    }
}

@BindingAdapter("adapter")
fun bindAdapter(view: ViewPager2, adapter: ViewPageAdapter){
    view.adapter = adapter
}
