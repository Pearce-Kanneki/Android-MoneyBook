package com.kanneki.moneybook.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerview<T: Any, VB: ViewDataBinding>
    : RecyclerView.Adapter<BaseViewHolder<VB>>() {

    protected val sections = ArrayList<T>()

    fun addSectionList(list: List<T>){
        sections.clear()
        sections.addAll(list)
        notifyDataSetChanged()
    }

    protected abstract fun getLayoutResId(): Int

    protected abstract fun setItemData(holder: VB, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {

        return BaseViewHolder(getView(parent))
    }

    override fun onBindViewHolder(binding: BaseViewHolder<VB>, position: Int) {
        setItemData(binding.binding, position)
    }

    override fun getItemCount(): Int = sections.size

    private fun getView(parent: ViewGroup): VB {

        val layoutInflater = LayoutInflater.from(parent.context)
        return DataBindingUtil.inflate(
                layoutInflater,
                getLayoutResId(),
                parent,
                false
        )
    }

}