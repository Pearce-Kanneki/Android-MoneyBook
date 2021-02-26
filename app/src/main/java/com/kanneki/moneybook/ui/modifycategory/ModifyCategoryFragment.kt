package com.kanneki.moneybook.ui.modifycategory

import androidx.fragment.app.viewModels
import com.kanneki.moneybook.R
import com.kanneki.moneybook.base.BaseFragment
import com.kanneki.moneybook.databinding.FragmentModifyCategoryBinding
import com.kanneki.moneybook.model.TypeItemEntity
import com.kanneki.moneybook.ui.dialog.ModifyBottomSheetDialog
import com.kanneki.moneybook.ui.dialog.UiDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ModifyCategoryFragment
    : BaseFragment<ModifyCategoryViewModel, FragmentModifyCategoryBinding>() {

    private val titleArray by lazy {
        resources.getStringArray(R.array.record_title)
    }

    private val modifyCategoryAdapter = ModifyCategoryAdapter{result ->
        viewModel.setSelectData(result)
        bottomSheetDialog.setTitle(result.name)
        bottomSheetDialog.show(childFragmentManager, ModifyBottomSheetDialog.TAG_DIALOG)
    }

    private val bottomSheetDialog = ModifyBottomSheetDialog{ type ->
        when(type){
            ModifyBottomSheetDialog.DIALOG_TYPE_MODIFY -> {
                UiDialog.editTextDialog(requireView()){ result ->
                    result?.let {
                        bottomSheetDialogModify(it)
                    }
                }
            }
            ModifyBottomSheetDialog.DIALOG_TYPE_DELETE -> {
                UiDialog.messageMessageDialog(
                    requireView(),
                    getString(R.string.dialog_message_delete, viewModel.getSelectData()?.name)
                ){dialogResult ->
                    if (dialogResult == true){
                        bottomSheetDialogDelete()
                    }
                }
            }
        } // end when
    }

    private fun bottomSheetDialogModify(newName: String){
        viewModel.getSelectData()?.let {
            val newData = it.apply { name = newName }
            GlobalScope.launch {
                viewModel.updateData(newData).flowOn(Dispatchers.IO).collectLatest {
                    viewModel.setSelectData(null)
                    viewModel.searchData(viewModel.toolBarTitle.get() != titleArray[0])
                }
            }
        }
    }

    private fun bottomSheetDialogDelete(){
        viewModel.getSelectData()?.let {
            GlobalScope.launch {
                viewModel.deleteData(it).flowOn(Dispatchers.IO).collectLatest {
                    viewModel.setSelectData(null)
                    viewModel.searchData(viewModel.toolBarTitle.get() != titleArray[0])
                }
            }
        }
    }

    override val viewModel: ModifyCategoryViewModel by viewModels()

    override fun isActivityBottomNavigationViewShow(): Boolean = true

    override fun getLayoutRes(): Int = R.layout.fragment_modify_category

    override fun initBindingData() {
        super.initBindingData()
        with(binding){
            vm = viewModel
            adapter = modifyCategoryAdapter
        }
    }

    override fun initData() {
        super.initData()
        viewModel.searchData(false)
        viewModel.setToolBarTitle(titleArray[0])
    }

    override fun initView() {
        super.initView()
        viewModel.isAddClick.observe(viewLifecycleOwner){ type ->
            if (type){
                viewModel.setIsAddClick()
                UiDialog.editTextDialog(requireView(), R.string.dialog_title_add){dialogType ->
                    dialogType?.let {
                        GlobalScope.launch{
                            val newData = TypeItemEntity().apply {
                                name = it
                                isIncome = viewModel.toolBarTitle.get() != titleArray[0]
                                codeType = viewModel.getCountItem() + 1
                            }
                            viewModel.insertData(newData).flowOn(Dispatchers.IO).collectLatest {
                                viewModel.searchData(viewModel.toolBarTitle.get() != titleArray[0])
                                viewModel.countItem()
                            }
                        }
                    }
                }
            }
        }

        viewModel.onClickToolBar.observe(viewLifecycleOwner){ type ->
            if (type){
                viewModel.setIsAddClick()
                UiDialog.selectItemDialog(
                    requireView(),
                    titleArray
                ){
                    viewModel.setToolBarTitle(titleArray[it])
                    viewModel.searchData(viewModel.toolBarTitle.get() != titleArray[0])
                }
            }
        }
    }

}