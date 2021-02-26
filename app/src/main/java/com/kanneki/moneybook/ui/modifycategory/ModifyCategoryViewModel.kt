package com.kanneki.moneybook.ui.modifycategory

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kanneki.moneybook.model.TypeItemEntity
import com.kanneki.moneybook.repository.type.TypeItemHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ModifyCategoryViewModel : ViewModel() {

    val data: ObservableArrayList<TypeItemEntity> = ObservableArrayList()
    val toolBarTitle: ObservableField<String> = ObservableField("")
    private val selectData: MutableLiveData<TypeItemEntity> = MutableLiveData()
    private val _isAddClick: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAddClick: LiveData<Boolean> get() = _isAddClick
    private val _onClickToolBar: MutableLiveData<Boolean> = MutableLiveData(false)
    val onClickToolBar: LiveData<Boolean> get() = _onClickToolBar
    private val TypeListCount: MutableLiveData<Int> = MutableLiveData(0)

    fun setToolBarTitle(title: String) {
        toolBarTitle.set(title)
    }

    fun searchData(isInMove: Boolean) {
        GlobalScope.launch {
            flow {
                val list = TypeItemHelper().searchList(isInMove)
                emit(list)
            }.flowOn(Dispatchers.IO).collect {
                data.clear()
                data.addAll(it)
            }
        }
    }

    fun countItem() {
        GlobalScope.launch {
            flow {
                emit(TypeItemHelper().countItem())
            }.flowOn(Dispatchers.IO).collect {
                TypeListCount.postValue(it)
            }
        }
    }

    fun getCountItem(): Int {
        return TypeListCount.value ?: 0
    }

    fun setSelectData(itemEntity: TypeItemEntity?) {
        selectData.postValue(itemEntity)
    }

    fun getSelectData(): TypeItemEntity? {
        return selectData.value
    }

    suspend fun insertData(data: TypeItemEntity): Flow<Long> {
        return flow {
            emit(TypeItemHelper().insert(data))
        }
    }

    suspend fun updateData(data: TypeItemEntity): Flow<Int> {
        return flow {
            emit(TypeItemHelper().update(data))
        }
    }

    suspend fun deleteData(data: TypeItemEntity): Flow<Int> {
        return flow {
            emit(TypeItemHelper().delete(data))
        }
    }

    fun setIsAddClick(type: Boolean = false) {
        _isAddClick.postValue(type)
    }

    fun setOnClickToolBar(type: Boolean = false) {
        _onClickToolBar.postValue(type)
    }
}