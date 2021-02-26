package com.kanneki.moneybook.ui.recordcategory

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.kanneki.moneybook.R
import com.kanneki.moneybook.base.BaseFragment
import com.kanneki.moneybook.databinding.FragmentRecordCategoryBinding

class RecordCategoryFragment
    : BaseFragment<RecordCategoryViewModel,FragmentRecordCategoryBinding>() {

    companion object{
        const val SEARCH_TYPE = "recordCategory_type"

        fun newInstance(type: Int): RecordCategoryFragment {
            val args = Bundle()
            args.putInt(SEARCH_TYPE, type)
            val fragment = RecordCategoryFragment()
            fragment.arguments = args

            return fragment
        }
    }

    private val typeCode by lazy {
        arguments?.getInt(SEARCH_TYPE) ?: 99
    }
    private val categoryAdapter = RecordCategoryAdapter()

    override val viewModel: RecordCategoryViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_record_category

    override fun isActivityBottomNavigationViewShow(): Boolean = true

    override fun initBindingData() {
        super.initBindingData()
        with(binding){
            adapter = categoryAdapter
            vm = viewModel
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.searchData(typeCode)
    }
}