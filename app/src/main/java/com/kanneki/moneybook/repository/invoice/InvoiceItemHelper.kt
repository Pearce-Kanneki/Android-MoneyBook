package com.kanneki.moneybook.repository.invoice

import com.kanneki.moneybook.model.InvoiceItemEntity

class InvoiceItemHelper {

    val db = InvoiceItemDataBase.invoke()

    fun searchById(id: String): List<InvoiceItemEntity>{
        return db.getInvoiceItemDao().findItems(id)
    }

    fun insert(item: InvoiceItemEntity): Long{
        return db.getInvoiceItemDao().insertItem(item)
    }

    fun update(item: InvoiceItemEntity): Int{
        return db.getInvoiceItemDao().updateItem(item)
    }

    fun delete(item: InvoiceItemEntity): Int{
        return db.getInvoiceItemDao().deleteItem(item)
    }
}