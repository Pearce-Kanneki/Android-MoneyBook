package com.kanneki.moneybook.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kanneki.moneybook.model.InvoiceItemEntity
import com.kanneki.moneybook.model.TypeItemEntity
import com.kanneki.moneybook.model.ViewCategoryModule
import com.kanneki.moneybook.ui.modifycategory.ModifyCategoryAdapter
import com.kanneki.moneybook.ui.recordcategory.RecordCategoryAdapter
import com.kanneki.moneybook.ui.recorddetail.RecordDetailAdapter
import com.kanneki.moneybook.utils.DividerItemDecoratorLocal

@BindingAdapter("adapter")
fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>){
    view.adapter = adapter
}

@BindingAdapter("adapterRecordDetailList")
fun bindAdapterRecordDetailList(view: RecyclerView, list: List<InvoiceItemEntity>?){
    list?.let {
        (view.adapter as? RecordDetailAdapter)?.addSectionList(it)
    }
}

@BindingAdapter("adapterCategoryList")
fun bindAdapterCategoryList(view: RecyclerView, list: List<ViewCategoryModule>?){
    list?.let {
        (view.adapter as? RecordCategoryAdapter)?.addSectionList(it)
    }
}

@BindingAdapter("adapterModifyCategoryList")
fun bindAdapterModifyCategoryList(view: RecyclerView, list: List<TypeItemEntity>?){
    list?.let {
        (view.adapter as? ModifyCategoryAdapter)?.addSectionList(it)
    }
}

@BindingAdapter("addItemDecoration")
fun bindAdapterAddItemDecoration(view: RecyclerView, dividerItemDecoration: DividerItemDecoratorLocal){
    view.addItemDecoration(dividerItemDecoration)
}




