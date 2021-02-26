package com.kanneki.moneybook.ui.record

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kanneki.moneybook.ui.recordcell.RecordCellFragment

class RecordViewModel: ViewModel() {

    val fragments: List<Fragment> = listOf(
        RecordCellFragment.newInstance(0),
        RecordCellFragment.newInstance(1)
    )
    private val _isGoModifyRecord: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val isGoModifyRecord: LiveData<Boolean> get() = _isGoModifyRecord


    fun goModifyRecord(view: View){
        setIsGoModifyRecord(true)
    }

    fun setIsGoModifyRecord(type: Boolean){
        _isGoModifyRecord.postValue(type)
    }

}