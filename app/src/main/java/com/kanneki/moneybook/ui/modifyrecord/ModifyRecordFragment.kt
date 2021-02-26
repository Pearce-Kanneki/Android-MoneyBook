package com.kanneki.moneybook.ui.modifyrecord

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kanneki.moneybook.R
import com.kanneki.moneybook.activityresult.contract.ScannerResultContract
import com.kanneki.moneybook.activityresult.observer.RequestPermissionObserver
import com.kanneki.moneybook.base.BaseFragment
import com.kanneki.moneybook.databinding.FragmentModifyRecordBinding

class ModifyRecordFragment
    : BaseFragment<ModifyRecordViewModel, FragmentModifyRecordBinding>() {

    companion object{
        const val BUNDLE_IS_INCOME = "IS_COME"
        const val BUNDLE_MODIFY_DATA = "MODIFY"
    }

    lateinit var observerLife: RequestPermissionObserver

    private val activityLauncher =
            registerForActivityResult(ScannerResultContract()){ result ->
                // TODO Scanner
                viewModel.scannerDataShow(result)
            }

    override val viewModel: ModifyRecordViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_modify_record

    override fun initData() {
        super.initData()
        viewModel.finishBlock.observe(this){ type ->
            if (type) findNavController().popBackStack()
        }
        observerLife = RequestPermissionObserver(
                requireActivity().activityResultRegistry
        ){ result ->
            if (result) activityLauncher.launch("")
        }
        lifecycle.addObserver(observerLife)
        viewModel.initActivityLauncher(observerLife)
        getFragmentArguments()
    }

    override fun initBindingData() {
        super.initBindingData()
        binding.vm = viewModel
    }

    private fun getFragmentArguments(){
        val type = arguments?.getBoolean(BUNDLE_IS_INCOME) ?: false
        val data = arguments?.getString(BUNDLE_MODIFY_DATA) ?: ""
        with(viewModel){
            initTypeList(type)
            isIncome.set(type)
            initData(data)
        }
    }
}