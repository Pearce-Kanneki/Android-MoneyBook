package com.kanneki.moneybook.ui.recordcategory

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.kanneki.moneybook.MoneyBookApp
import com.kanneki.moneybook.R
import com.kanneki.moneybook.model.InvoiceItemEntity
import com.kanneki.moneybook.model.TypeItemEntity
import com.kanneki.moneybook.model.ViewCategoryModule
import com.kanneki.moneybook.repository.invoice.InvoiceItemHelper
import com.kanneki.moneybook.repository.type.TypeItemHelper
import com.kanneki.moneybook.utils.DividerItemDecoratorLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecordCategoryViewModel: ViewModel() {

    val data: ObservableArrayList<ViewCategoryModule> = ObservableArrayList()
    val divider = DividerItemDecoratorLocal(
        ContextCompat.getDrawable(
            MoneyBookApp.getContext(),
            R.drawable.divider
        )
    )

    @SuppressLint("SimpleDateFormat")
    fun searchData(type: Int) = GlobalScope.launch {
        val searchTypeList = flow {
            if(!(type == 0 || type == 1)){
                emit(listOf<TypeItemEntity>())
            }
            emit(TypeItemHelper().searchList(type == 1))
        }
        flow {
            if(!(type == 0 || type == 1)){
                emit(listOf<InvoiceItemEntity>())
            }
            val date = Date()
            val formatter = SimpleDateFormat("yyyyMM")
            val dateString = formatter.format(date)
            val result = InvoiceItemHelper().searchById("$type$dateString")
            emit(result)
        }.zip(searchTypeList){list, typeList ->
            val maxIndex = list.maxByOrNull { it.buyType }?.buyType ?: 0
            val zipData = ArrayList<ViewCategoryModule>()
            for (i in 0..maxIndex){
                val tmp = list.filter { x -> x.buyType == i }
                if (tmp.isNotEmpty()){
                    val model = ViewCategoryModule(
                            title = typeList.find { x -> x.codeType == i }?.name ?: "",
                            type = i,
                            sum = tmp.sumBy { it.price }
                    )
                    zipData.add(model)
                }
            }
            zipData
        }.flowOn(Dispatchers.IO).collect {
            data.clear()
            data.addAll(it)
        }
    }

}