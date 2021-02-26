package com.kanneki.moneybook.repository.type

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kanneki.moneybook.MoneyBookApp
import com.kanneki.moneybook.model.TypeItemEntity

@Database(entities = [(TypeItemEntity::class)], version = 1)
abstract class TypeItemDataBase: RoomDatabase() {

    companion object{
        @Volatile private var instance: TypeItemDataBase? = null
        private val LOCK = Any()

        operator fun invoke() = instance ?: synchronized(LOCK){
            instance ?: buildDataBase().also {
                instance = it
            }
        }

        private fun buildDataBase() = Room.databaseBuilder(
                MoneyBookApp.getContext(),
                TypeItemDataBase::class.java,
                TypeItemDataBase::class.java.simpleName
        ).build()
    }

    abstract fun getTypeItemDao(): TypeItemDAO
}