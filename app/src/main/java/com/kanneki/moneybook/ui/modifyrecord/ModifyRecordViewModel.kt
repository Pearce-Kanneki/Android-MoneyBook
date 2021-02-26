package com.kanneki.moneybook.ui.modifyrecord

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kanneki.moneybook.activityresult.observer.RequestPermissionObserver
import com.kanneki.moneybook.model.InvoiceItemEntity
import com.kanneki.moneybook.model.TypeItemEntity
import com.kanneki.moneybook.repository.invoice.InvoiceItemHelper
import com.kanneki.moneybook.repository.type.TypeItemHelper
import com.kanneki.moneybook.ui.dialog.UiDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class ModifyRecordViewModel: ViewModel() {

    val finishBlock = MutableLiveData(false)
    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    val money: ObservableField<String> = ObservableField()
    val itemName: ObservableField<String> = ObservableField()
    val recordDate: ObservableField<String> = ObservableField()
    val recordType: ObservableField<String> = ObservableField()
    val note: ObservableField<String> = ObservableField()
    val receipt: ObservableField<String> = ObservableField()
    val typeList: ObservableArrayList<TypeItemEntity> = ObservableArrayList()
    val isIncome: ObservableBoolean = ObservableBoolean(false)
    private val isModifyRecordData: MutableLiveData<InvoiceItemEntity> = MutableLiveData()
    lateinit var observer: RequestPermissionObserver


    fun showDialog(view: View){
        UiDialog.dateDialog(view){result ->
            result?.let {recordDate.set(it)}
        } // end UiDialog
    }

    fun initActivityLauncher(launcher: RequestPermissionObserver){
        observer = launcher
    }

    fun qrcodeScanner() {
        observer.requestPermissionCamera()
    }

    fun initTypeList(isInCome: Boolean) {
       GlobalScope.launch {
           flow {
               emit(TypeItemHelper().searchList(isInCome))
           }.flowOn(Dispatchers.IO).collect {
               typeList.addAll(it)
           }
       }
    }

    fun initData(str: String){
        try {
            Gson().fromJson(str, InvoiceItemEntity::class.java)?.let { data ->
                initShowData(data)
                isModifyRecordData.postValue(data)
            }
        }catch (e: Exception){

        }
    }

    fun openSelectTypeDialog(view: View){
        val selectList: Array<String> = typeList.map { x -> x.name }.toTypedArray()
        UiDialog.selectItemDialog(view, selectList){
            recordType.set(selectList[it])
        }
    }

    fun scannerDataShow(str: String){
        Gson().fromJson(str, InvoiceItemEntity::class.java)?.let { data ->
            initShowData(data)
        }
    }

    fun setFinishBlock(type: Boolean) {
        finishBlock.postValue(type)
    }

    private fun initShowData(data: InvoiceItemEntity){
        itemName.set(data.desc)
        money.set(data.price.toString())
        note.set(data.note)
        receipt.set(data.receipt)
        recordDate.set(simpleDateFormat.format(data.buyDate))
        data.buyTypeText?.let {
            recordType.set(it)
        }
    }

    fun submitData(){
        isModifyRecordData.value?.let {
            updataSubmitData(it)
        }?: run {
            insertSubmitData()
        }
    }

    private fun insertSubmitData() = GlobalScope.launch {
        flow {
            val data = InvoiceItemEntity().apply {
                desc = itemName.get() ?: ""
                price = money.get()?.toIntOrNull() ?: 0
                buyType = typeList.find { x -> x.name == recordType.get() }?.codeType ?: 0
                note = this@ModifyRecordViewModel.note.get() ?: ""
                receipt = this@ModifyRecordViewModel.receipt.get()
                recordDate.get()?.let {
                    buyDate = simpleDateFormat.parse(it)
                }
                setIdKey(typeList.first().isIncome)
            }
            val result = InvoiceItemHelper().insert(data)
            emit(result)
        }.flowOn(Dispatchers.IO).catch {
            Log.d("TAG", "Submit Error")
        }.collect {
            if (it >= 1){finishBlock.postValue(true)}
        }
    }

    private fun updataSubmitData(data: InvoiceItemEntity) = GlobalScope.launch {
        flow {
            data.apply {
                desc = itemName.get() ?: ""
                price = money.get()?.toIntOrNull() ?: 0
                buyType = typeList.find { x -> x.name == recordType.get() }?.codeType ?: 0
                note = this@ModifyRecordViewModel.note.get() ?: ""
                receipt = this@ModifyRecordViewModel.receipt.get()
                recordDate.get()?.let {
                    buyDate = simpleDateFormat.parse(it)
                }
            }
            emit(InvoiceItemHelper().update(data))
        }.flowOn(Dispatchers.IO).collectLatest {
            if (it > 0) finishBlock.postValue(true)
        }
    }
}