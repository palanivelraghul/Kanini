package com.task.kaninieventvenu.model

import com.google.gson.annotations.SerializedName


class EventDates {
    @SerializedName("start")
    var eventStart: EventStartDate? = null

    @SerializedName("timezone")
    var timezone: String? = null
}
