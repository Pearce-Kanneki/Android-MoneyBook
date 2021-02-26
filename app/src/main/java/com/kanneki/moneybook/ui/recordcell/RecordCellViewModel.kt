package com.kanneki.moneybook.ui.recordcell

import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.kanneki.moneybook.ui.recordcategory.RecordCategoryFragment
import com.kanneki.moneybook.ui.recorddetail.RecordDetailFragment

class RecordCellViewModel: ViewModel() {


    val fragments: ObservableArrayList<Fragment> = ObservableArrayList()

    fun setFragments(type: Int){
        fragments.clear()

        fragments.addAll(listOf(
                RecordDetailFragment.newInstance(type),
                RecordCategoryFragment.newInstance(type)
        ))
    }

    fun getFragments(): List<Fragment> = fragments
}