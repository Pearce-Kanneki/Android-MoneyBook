package com.kanneki.moneybook.utils

import com.kanneki.moneybook.MoneyBookApp
import com.kanneki.moneybook.R
import com.kanneki.moneybook.model.TypeItemEntity

object MoneyBookTypeUtils {

    fun initTypeItem(): List<TypeItemEntity>{
        val context = MoneyBookApp.getContext()
        val list = ArrayList<TypeItemEntity>()
        context.resources.getStringArray(R.array.record_type).forEachIndexed { index, item ->
            val tmp = TypeItemEntity().apply {
                name = item
                codeType = index + 1
            }

            list.add(tmp)
        }

        context.resources.getStringArray(R.array.record_type_is).forEachIndexed { index, item ->
            val tmp = TypeItemEntity().apply {
                name = item
                codeType = list.size + 1
                isIncome = true
            }

            list.add(tmp)
        }
        return list
    }


}