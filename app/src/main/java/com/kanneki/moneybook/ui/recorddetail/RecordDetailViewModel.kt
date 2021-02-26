package com.kanneki.moneybook.ui.recorddetail

import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kanneki.moneybook.MoneyBookApp
import com.kanneki.moneybook.R
import com.kanneki.moneybook.model.InvoiceItemEntity
import com.kanneki.moneybook.repository.invoice.InvoiceItemHelper
import com.kanneki.moneybook.repository.type.TypeItemHelper
import com.kanneki.moneybook.utils.DividerItemDecoratorLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class RecordDetailViewModel: ViewModel() {

    val data: ObservableArrayList<InvoiceItemEntity> = ObservableArrayList()
    private val selectData: MutableLiveData<InvoiceItemEntity> = MutableLiveData()
    val divider = DividerItemDecoratorLocal(
            ContextCompat.getDrawable(
                    MoneyBookApp.getContext(),
                    R.drawable.divider
            )
    )

    fun searchDara(type: Int) {
        GlobalScope.launch {
            flow{
                if(!(type == 0 || type == 1)){
                    emit(listOf<InvoiceItemEntity>())
                }
                val result = InvoiceItemHelper().searchById(type.toString())
                    .sortedBy { item -> item.buyDate }

                searchTypeList(type, result)
                emit(result)
            }.flowOn(Dispatchers.IO).collect {
                data.clear()
                data.addAll(it)
            }
        }
    }

    private fun searchTypeList(type: Int, list: List<InvoiceItemEntity>){
        GlobalScope.launch {
            flow {
                emit(TypeItemHelper().searchList(type != 0))
            }.flowOn(Dispatchers.IO).collect {
                list.forEach { item ->
                    item.buyTypeText = it.find { x -> x.codeType == item.buyType }?.name ?: ""
                }
            }
        }
    }

    fun deleteData(itemEntity: InvoiceItemEntity): Flow<Int>{
        return flow {
            emit(InvoiceItemHelper().delete(itemEntity))
        }
    }

    fun setSelectData(invoiceItemEntity: InvoiceItemEntity?){
        selectData.postValue(invoiceItemEntity)
    }

    fun getSelectData(): InvoiceItemEntity?{
        return selectData.value
    }
}