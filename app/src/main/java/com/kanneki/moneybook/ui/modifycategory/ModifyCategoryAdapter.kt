package com.kanneki.moneybook.ui.modifycategory

import com.kanneki.moneybook.R
import com.kanneki.moneybook.base.BaseRecyclerview
import com.kanneki.moneybook.databinding.ItemModifyCategoryBinding
import com.kanneki.moneybook.model.TypeItemEntity

class ModifyCategoryAdapter(private val callback:(TypeItemEntity) -> Unit)
    : BaseRecyclerview<TypeItemEntity, ItemModifyCategoryBinding>() {

    override fun getLayoutResId(): Int = R.layout.item_modify_category

    override fun setItemData(holder: ItemModifyCategoryBinding, position: Int) {
        holder.apply {
            item = sections[position]
            root.setOnClickListener {
                callback(sections[position])
            }
            executePendingBindings()
        }
    }
}