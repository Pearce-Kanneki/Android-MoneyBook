package com.kanneki.moneybook.repository.type

import androidx.room.*
import com.kanneki.moneybook.model.TypeItemEntity

@Dao
interface TypeItemDAO {

    @Query("SELECT * FROM ${TypeItemEntity.TABLE_NAME} WHERE isIncome = :isIncome")
    fun findItems(isIncome: Boolean): List<TypeItemEntity>

    @Query("SELECT COUNT(*) FROM ${TypeItemEntity.TABLE_NAME}")
    fun countItem(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: TypeItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListItem(list: List<TypeItemEntity>): List<Long>

    @Update
    fun updateItem(item: TypeItemEntity): Int

    @Delete
    fun deleteItem(item: TypeItemEntity): Int
}
