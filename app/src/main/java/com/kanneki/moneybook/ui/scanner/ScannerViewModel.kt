package com.kanneki.moneybook.ui.scanner

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.kanneki.moneybook.model.InvoiceItemEntity
import java.text.SimpleDateFormat

class ScannerViewModel: ViewModel() {

    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("yyyyMMdd")

    fun StringToModel(str: String): InvoiceItemEntity {

        val resultList = str.split(":")
        if (resultList.size > 5 && resultList[0].length == 77){

            val result = resultList[0]
            val data = InvoiceItemEntity().apply {
                desc = ""
                receipt = result.substring(0, 10)
                buyDate = simpleDateFormat.parse(
                        (result.substring(10, 17).toInt() + 19110000).toString()
                )
                price = result.substring(29, 37).toInt(16)
                note = ""
            }

            for (i in 5 until resultList.size){
                when((i-5) % 3){
                    0 -> data.note += resultList[i].trim()
                    1 -> data.note += "*${resultList[i].trim()}"
                    2 -> data.note += " (${resultList[i].trim()}) \r\n"
                }
            }

            return data
        } else {
            return InvoiceItemEntity()
        }

    }
}