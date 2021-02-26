package com.kanneki.moneybook.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {

    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("yyyy-MM-dd")

    fun formatDate(timestamp: Long): String {
        return formatter.format(Date(timestamp))
    }
}