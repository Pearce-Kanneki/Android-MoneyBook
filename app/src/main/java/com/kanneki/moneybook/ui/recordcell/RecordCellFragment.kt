package com.kanneki.moneybook.ui.recordcell

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.kanneki.moneybook.R
import com.kanneki.moneybook.adapter.ViewPageAdapter
import com.kanneki.moneybook.base.BaseFragment
import com.kanneki.moneybook.databinding.CellTablayoutViewpageBinding
import kotlinx.android.synthetic.main.cell_tablayout_viewpage.*

class RecordCellFragment : BaseFragment<RecordCellViewModel, CellTablayoutViewpageBinding>(){

    companion object {
        const val SEARCH_TYPE = "recordCell_type"

        fun newInstance(type: Int): RecordCellFragment {
            val args = Bundle()
            args.putInt(SEARCH_TYPE, type)
            val fragment = RecordCellFragment()
            fragment.arguments = args

            return fragment
        }
    }

    private val title by lazy {
        resources.getStringArray(R.array.record_cell_title)
    }

    override val viewModel: RecordCellViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.cell_tablayout_viewpage

    override fun isActivityBottomNavigationViewShow(): Boolean = true

    override fun initData() {
        super.initData()
        val typeCode = arguments?.getInt(SEARCH_TYPE) ?: 99
        viewModel.setFragments(typeCode)
    }

    override fun initView() {
        super.initView()
        val pageAdapter = ViewPageAdapter(childFragmentManager, lifecycle)
        pageAdapter.addFragments(
                viewModel.getFragments()
        )
        cell_viewpage.adapter = pageAdapter
        TabLayoutMediator(cell_tablayout, cell_viewpage){ tab, position ->
            if (title.size > position) tab.text = title[position]
        }.attach()
    }

}