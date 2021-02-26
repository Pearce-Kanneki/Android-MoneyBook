package com.kanneki.moneybook.repository.initdata

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import com.kanneki.moneybook.MoneyBookApp
import kotlinx.coroutines.flow.map

class InitDataRespository {

    companion object {

        val PREFERENCE_NAME = "settings"
        val pKey = "init_data"
        val dataStore: DataStore<Preferences> = MoneyBookApp.getContext().createDataStore(
            name = PREFERENCE_NAME
        )

        val keyBack = booleanPreferencesKey(pKey)

        suspend fun insertDataStroe(type : Boolean){

            dataStore.edit { setting ->

                setting[keyBack] = type
            }
        }

        val getDataStrore = dataStore.data.map { cps -> cps[keyBack] ?: false }
    }
}