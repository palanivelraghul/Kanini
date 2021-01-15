package com.task.kaninieventvenu.model

import com.google.gson.annotations.SerializedName


class EventStartDate {

    @SerializedName("localDate")
    var localDate: String? = null

    @SerializedName("localTime")
    var localTime: String? = null

    @SerializedName("dateTime")
    var dateTime: String? = null
}
