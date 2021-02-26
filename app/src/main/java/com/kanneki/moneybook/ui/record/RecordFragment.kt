package com.kanneki.moneybook.ui.record

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.kanneki.moneybook.R
import com.kanneki.moneybook.adapter.ViewPageAdapter
import com.kanneki.moneybook.base.BaseFragment
import com.kanneki.moneybook.databinding.FragmentRecordBinding
import com.kanneki.moneybook.ui.dialog.UiDialog
import com.kanneki.moneybook.ui.modifyrecord.ModifyRecordFragment
import kotlinx.android.synthetic.main.fragment_record.*

class RecordFragment: BaseFragment<RecordViewModel, FragmentRecordBinding>() {

    private val title by lazy {
        resources.getStringArray(R.array.record_title)
    }

    override val viewModel: RecordViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_record

    override fun isActivityBottomNavigationViewShow(): Boolean = true

    override fun initBindingData() {
        super.initBindingData()
        with(binding){
            vm = viewModel
            lifecycleOwner = this@RecordFragment
        }
    }

    override fun initView() {
        super.initView()
        val pageAdapter = ViewPageAdapter(childFragmentManager, lifecycle)
        pageAdapter.addFragments(viewModel.fragments)
        RecordViewPage.adapter = pageAdapter
        TabLayoutMediator(RecordTabLayout, RecordViewPage){tab, position ->
            tab.text = title[position]
        }.attach()
        viewModel.isGoModifyRecord.observe(viewLifecycleOwner){type ->
            if (type){
                UiDialog.selectTypeDialog(requireView()){
                    val bundle = Bundle().apply {
                        putBoolean(ModifyRecordFragment.BUNDLE_IS_INCOME, it ?: false)
                    }
                    findNavController().navigate(R.id.action_navigation_record_to_modifyRecord, bundle)
                    viewModel.setIsGoModifyRecord(false)
                }
            }
        }
    }
}