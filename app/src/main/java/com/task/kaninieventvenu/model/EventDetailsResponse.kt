package com.task.kaninieventvenu.model

import com.google.gson.annotations.SerializedName

class EventDetailsResponse {

    @SerializedName("_embedded")
    var mEventDetailsEmbeddedList: EventDetailsEmbeddedList? = null

    @SerializedName("page")
    var mEventDetailsResponseCount: EventDetailsResponseCount? = null

}
