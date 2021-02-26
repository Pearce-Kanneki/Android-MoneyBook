package com.kanneki.moneybook.repository.type

import com.kanneki.moneybook.model.TypeItemEntity

class TypeItemHelper {

    val db = TypeItemDataBase.invoke()

    fun searchList(isInMove: Boolean): List<TypeItemEntity>{
        return db.getTypeItemDao().findItems(isInMove)
    }

    fun countItem(): Int{
        return db.getTypeItemDao().countItem()
    }

    fun insert(item: TypeItemEntity): Long {
        return db.getTypeItemDao().insertItem(item)
    }

    fun insertList(list: List<TypeItemEntity>): List<Long> {
        return db.getTypeItemDao().insertListItem(list)
    }

    fun update(item: TypeItemEntity): Int {
        return db.getTypeItemDao().updateItem(item)
    }

    fun delete(item: TypeItemEntity): Int {
        return db.getTypeItemDao().deleteItem(item)
    }
}