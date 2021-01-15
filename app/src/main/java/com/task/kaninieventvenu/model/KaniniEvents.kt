package com.task.kaninieventvenu.model

import com.google.gson.annotations.SerializedName


class KaniniEvents {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("id")
    var id: String? = null

    @SerializedName("test")
    var test: Boolean? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("dates")
    var eventDates: EventDates? = null

    @SerializedName("info")
    var info: String? = null

    @SerializedName("_embedded")
    var mEventEmbeddedDetails: EventEmbeddedDetails? = null
}
