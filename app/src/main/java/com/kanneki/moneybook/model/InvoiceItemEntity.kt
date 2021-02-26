package com.kanneki.moneybook.model

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kanneki.moneybook.model.InvoiceItemEntity.Companion.TABLE_NAME
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = TABLE_NAME)
class InvoiceItemEntity {

    companion object {
        const val TABLE_NAME = "invoice_entity"
    }

    @PrimaryKey(autoGenerate = false) var id: String = ""
    var desc: String = ""
    var note: String = ""
    var price: Int = 0
    var buyDate: Date? = null
    var buyType: Int = 0
    var receipt: String? = null
    var buyTypeText: String? = null


    @SuppressLint("SimpleDateFormat")
    fun setIdKey(type: Boolean){
        // type => false: 支出 0, true: 收入 1

        val rand = UUID.randomUUID().toString().split("-")
        val dateFormat: DateFormat = SimpleDateFormat("yyyyMMdd")
        id = "${if (type) 1 else 0}${dateFormat.format(buyDate)}${rand[0]}"
    }

}