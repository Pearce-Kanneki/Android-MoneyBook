package com.kanneki.moneybook.repository.invoice

import androidx.room.*
import com.kanneki.moneybook.model.InvoiceItemEntity

@Dao
interface InvoiceItemDAO {

    @Query("SELECT * FROM ${InvoiceItemEntity.TABLE_NAME} WHERE id LIKE :key || '%'")
    fun findItems(key: String): List<InvoiceItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: InvoiceItemEntity): Long

    @Update
    fun updateItem(item: InvoiceItemEntity): Int

    @Delete
    fun deleteItem(item: InvoiceItemEntity): Int
}