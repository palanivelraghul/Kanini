package com.task.kaninieventvenu.model

import com.google.gson.annotations.SerializedName


class EventVenueState {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("stateCode")
    var stateCode: String? = null
}
