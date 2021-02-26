package com.kanneki.moneybook.repository.invoice

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kanneki.moneybook.MoneyBookApp
import com.kanneki.moneybook.model.InvoiceItemEntity
import com.kanneki.moneybook.utils.Converters

@Database(entities = [(InvoiceItemEntity::class)], version = 1)
@TypeConverters(Converters::class)
abstract class InvoiceItemDataBase: RoomDatabase() {

    companion object{
        @Volatile private var instance: InvoiceItemDataBase? = null
        private val LOCK = Any()

        operator fun invoke() = instance ?: synchronized(LOCK){
            instance ?: buildDataBase().also {
                instance = it
            }
        }

        private fun buildDataBase() = Room.databaseBuilder(
                MoneyBookApp.getContext(),
                InvoiceItemDataBase::class.java,
                InvoiceItemDataBase::class.java.simpleName
        ).build()
    }

    abstract fun getInvoiceItemDao(): InvoiceItemDAO
}