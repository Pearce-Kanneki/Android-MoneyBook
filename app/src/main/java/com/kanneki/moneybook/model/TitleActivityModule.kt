package com.kanneki.moneybook.model

data class TitleActivityModule(
    val title: String,
    val actionId: Int?,
    var type: Int = NOT,
    var switchValue: Boolean = true
) {
    companion object{
        const val NOT = 0
        const val START_ACTIVITY = 1
        const val TYPE_DIALOG = 2
        const val TYPE_SWITCH = 3
    }
}