package com.task.kaninieventvenu.model

import com.google.gson.annotations.SerializedName


class EventEmbeddedDetails {

    @SerializedName("venues")
    var venues: List<EventVenueDetails>? = null
}
