package com.task.kaninieventvenu.utils

import java.util.*

object AppUtils {

    fun getCurrencyFormatedValue(amount: Double): String {
        return "\u20B9" + String.format(Locale.getDefault(), "%.2f", amount)
    }

}
