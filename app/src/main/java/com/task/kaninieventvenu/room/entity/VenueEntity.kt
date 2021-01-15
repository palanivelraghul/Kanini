package com.task.kaninieventvenu.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "VenueTable")
data class VenueEntity(
    @PrimaryKey
    @ColumnInfo(name = "VenueId")
    var venueId: String,
    @ColumnInfo(name = "VenueEventId")
    var venueEventId: String,
    @ColumnInfo(name = "city")
    var city: String,
    @ColumnInfo(name = "VenueName")
    var venueName: String,
    @ColumnInfo(name = "State")
    var state: String,
    @ColumnInfo(name = "Country")
    var country: String,
    @ColumnInfo(name = "Longitude")
    var longitude: String,
    @ColumnInfo(name = "Latitude")
    var latitude: String
)