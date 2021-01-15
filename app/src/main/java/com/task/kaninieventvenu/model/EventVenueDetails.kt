package com.task.kaninieventvenu.model

import com.google.gson.annotations.SerializedName


class EventVenueDetails {

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

    @SerializedName("locale")
    var locale: String? = null

    @SerializedName("postalCode")
    var postalCode: String? = null

    @SerializedName("timezone")
    var timezone: String? = null

    @SerializedName("city")
    var city: EventVenueCity? = null

    @SerializedName("state")
    var state: EventVenueState? = null

    @SerializedName("country")
    var country: EventVenueCountry? = null

    @SerializedName("location")
    var location: EventVenueLocation? = null
}
