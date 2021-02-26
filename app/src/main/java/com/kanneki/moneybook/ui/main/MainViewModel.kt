package com.kanneki.moneybook.ui.main

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.kanneki.moneybook.repository.initdata.InitDataRespository
import com.kanneki.moneybook.repository.type.TypeItemHelper
import com.kanneki.moneybook.utils.MoneyBookTypeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val isBottomNavigationViewShow = ObservableBoolean(true)

    suspend fun initType() {
        InitDataRespository.getDataStrore
            .flowOn(Dispatchers.IO).collectLatest { isInitData ->
                if (!isInitData) {
                    GlobalScope.launch(Dispatchers.IO) {
                        TypeItemHelper().insertList(MoneyBookTypeUtils.initTypeItem())
                        InitDataRespository.insertDataStroe(true)
                    }
                }
            }
    }

    fun setBottomNavigationViewShow(type: Boolean = false) {
        isBottomNavigationViewShow.set(type)
    }
}