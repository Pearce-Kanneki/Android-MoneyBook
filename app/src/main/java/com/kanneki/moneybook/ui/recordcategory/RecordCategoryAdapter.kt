package com.kanneki.moneybook.ui.recordcategory

import com.kanneki.moneybook.R
import com.kanneki.moneybook.base.BaseRecyclerview
import com.kanneki.moneybook.databinding.ItemRecordCategoryCellBinding
import com.kanneki.moneybook.model.ViewCategoryModule

class RecordCategoryAdapter
    : BaseRecyclerview<ViewCategoryModule, ItemRecordCategoryCellBinding>(){

    override fun getLayoutResId(): Int = R.layout.item_record_category_cell

    override fun setItemData(binding: ItemRecordCategoryCellBinding, position: Int) {
        binding.item = sections[position]
    }
}