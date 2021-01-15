package com.task.kaninieventvenu.model

import com.google.gson.annotations.SerializedName


class EventVenueLocation {


    @SerializedName("longitude")
    var longitude: String? = null

    @SerializedName("latitude")
    var latitude: String? = null
}
