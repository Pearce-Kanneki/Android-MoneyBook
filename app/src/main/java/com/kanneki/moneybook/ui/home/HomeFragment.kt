package com.kanneki.moneybook.ui.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kanneki.moneybook.R
import com.kanneki.moneybook.base.BaseFragment
import com.kanneki.moneybook.databinding.FragmentHomeBinding
import com.kanneki.moneybook.ui.dialog.UiDialog
import com.kanneki.moneybook.ui.modifyrecord.ModifyRecordFragment

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun isActivityBottomNavigationViewShow(): Boolean = true

    override fun initBindingData() {
        super.initBindingData()

        binding.vm = viewModel
    }

    override fun initData() {
        super.initData()
        viewModel.initTodayMoney()
        viewModel.initMonthExp()
        viewModel.initMonthIncome()

    }

    override fun initView() {
        super.initView()
        viewModel.isGoModifyRecord.observe(viewLifecycleOwner){type ->
            if (type){
                UiDialog.selectTypeDialog(requireView()){
                    val bundle = Bundle().apply {
                        putBoolean(ModifyRecordFragment.BUNDLE_IS_INCOME, it ?: false)
                    }
                    findNavController().navigate(R.id.action_navigation_home_to_modifyRecord, bundle)
                    viewModel.setIsGoModifyRecord(false)
                }
            }
        }
    }

}