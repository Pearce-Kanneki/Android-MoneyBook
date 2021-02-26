package com.kanneki.moneybook.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kanneki.moneybook.model.TypeItemEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class TypeItemEntity {

    companion object {
        const val TABLE_NAME = "type_entity"
    }


    @PrimaryKey(autoGenerate = true)var id:Int = 0
    var codeType: Int = 0
    var isIncome: Boolean = false
    var name: String = ""

}