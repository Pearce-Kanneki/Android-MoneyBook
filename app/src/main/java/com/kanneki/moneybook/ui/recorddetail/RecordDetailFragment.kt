package com.kanneki.moneybook.ui.recorddetail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.kanneki.moneybook.R
import com.kanneki.moneybook.base.BaseFragment
import com.kanneki.moneybook.databinding.FragmentRecordDetailBinding
import com.kanneki.moneybook.model.InvoiceItemEntity
import com.kanneki.moneybook.ui.dialog.ModifyBottomSheetDialog
import com.kanneki.moneybook.ui.dialog.UiDialog
import com.kanneki.moneybook.ui.modifyrecord.ModifyRecordFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class RecordDetailFragment : BaseFragment<RecordDetailViewModel, FragmentRecordDetailBinding>() {

    companion object {

        const val SEARCH_TYPE = "recordDetail_type"

        fun newInstance(type: Int): RecordDetailFragment {
            val args = Bundle().apply {
                putInt(SEARCH_TYPE, type)
            }
            val fragment = RecordDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val typeCode by lazy {
        arguments?.getInt(SEARCH_TYPE) ?: 99
    }

    private val detailAdapter = RecordDetailAdapter {
        viewModel.setSelectData(it)
        bottomSheetDialog.setTitle(it.desc)
        bottomSheetDialog.show(childFragmentManager, ModifyBottomSheetDialog.TAG_DIALOG)
    }

    private val bottomSheetDialog = ModifyBottomSheetDialog { type ->
        when (type) {
            ModifyBottomSheetDialog.DIALOG_TYPE_MODIFY -> {
                UiDialog.selectTypeDialog(requireView()){ type ->
                    val build = Bundle().apply {
                        putString(ModifyRecordFragment.BUNDLE_MODIFY_DATA, Gson().toJson(viewModel.getSelectData()))
                        putBoolean(ModifyRecordFragment.BUNDLE_IS_INCOME, type ?: false)
                    }
                    findNavController().navigate(R.id.action_navigation_record_to_modifyRecord, build)
                }

            }
            ModifyBottomSheetDialog.DIALOG_TYPE_DELETE -> {
                UiDialog.messageMessageDialog(
                    requireView(),
                    getString(R.string.dialog_message_delete)
                ){ dialogType ->
                    if (dialogType == true){
                        GlobalScope.launch {
                            viewModel.deleteData(
                                viewModel.getSelectData() ?: InvoiceItemEntity()
                            ).flowOn(Dispatchers.IO).collect {
                                viewModel.setSelectData(null)
                                viewModel.searchDara(typeCode)
                            }
                        }
                    }
                }
            }
        } // end when
    }

    override val viewModel: RecordDetailViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_record_detail

    override fun isActivityBottomNavigationViewShow(): Boolean = true

    override fun initBindingData() {
        super.initBindingData()
        with(binding) {
            adapter = detailAdapter
            vm = viewModel
        }
    }

    override fun initData() {
        super.initData()
        viewModel.searchDara(typeCode)
    }
}