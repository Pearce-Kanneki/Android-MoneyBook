package com.kanneki.moneybook.ui.home

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kanneki.moneybook.repository.invoice.InvoiceItemHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class HomeViewModel: ViewModel() {

    private val dateFormatYMD = SimpleDateFormat("yyyyMMdd")
    private val dateFormatYM = SimpleDateFormat("yyyyMM")
    val todayMoney: ObservableField<String> = ObservableField("0")
    val monthExpenditureMoney: ObservableField<String> = ObservableField("0")
    val monthIncomeMoney: ObservableField<String> = ObservableField("0")
    private val _isGoModifyRecord: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val isGoModifyRecord: LiveData<Boolean> get() = _isGoModifyRecord

    fun initTodayMoney() = GlobalScope.launch {
        flow {
            val key = "0${dateFormatYMD.format(Date())}"
            emit(InvoiceItemHelper().searchById(key))
        }.flowOn(Dispatchers.IO).catch {

        }.collect {
            val sum = it.sumBy { x -> x.price }
            todayMoney.set(sum.toString())
        }
    }

    fun initMonthExp() = GlobalScope.launch {
        flow {
            val searchKey = "0${dateFormatYM.format(Date())}"
            emit(InvoiceItemHelper().searchById(searchKey))
        }.flowOn(Dispatchers.IO).collect {
            val sum = it.sumBy { x -> x.price }
            monthExpenditureMoney.set(sum.toString())
        }
    }

    fun initMonthIncome() = GlobalScope.launch {
        flow {
            val searchKey = "1${dateFormatYM.format(Date())}"
            emit(InvoiceItemHelper().searchById(searchKey))
        }.flowOn(Dispatchers.IO).collect {
            val sum = it.sumBy { x -> x.price }
            monthIncomeMoney.set(sum.toString())
        }
    }

    fun goModifyRecord(view: View){
        setIsGoModifyRecord(true)
    }

    fun setIsGoModifyRecord(type: Boolean){
        _isGoModifyRecord.postValue(type)
    }
}