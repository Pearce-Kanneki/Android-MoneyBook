package com.kanneki.moneybook.ui.recorddetail

import com.kanneki.moneybook.R
import com.kanneki.moneybook.base.BaseRecyclerview
import com.kanneki.moneybook.databinding.ItemRceordDetialCellBinding
import com.kanneki.moneybook.model.InvoiceItemEntity

class RecordDetailAdapter(private val callback: (InvoiceItemEntity) -> Unit)
    : BaseRecyclerview<InvoiceItemEntity, ItemRceordDetialCellBinding>(){

    override fun getLayoutResId(): Int = R.layout.item_rceord_detial_cell

    override fun setItemData(holder: ItemRceordDetialCellBinding, position: Int) {
        holder.apply {
            item = sections[position]
            executePendingBindings()
            root.setOnClickListener {
                callback(sections[position])
            }
        }
    }

}