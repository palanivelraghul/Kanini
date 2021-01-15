package com.task.kaninieventvenu.model

import com.google.gson.annotations.SerializedName

class EventDetailsEmbeddedList {

    @SerializedName("events")
    var mEventList: List<KaniniEvents>? = null

}
