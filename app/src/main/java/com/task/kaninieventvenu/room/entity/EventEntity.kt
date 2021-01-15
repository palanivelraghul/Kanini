package com.task.kaninieventvenu.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EventTable")
data class EventEntity(
    @PrimaryKey
    @ColumnInfo(name = "EventId")
    var eventId: String,
    @ColumnInfo(name = "Name")
    var eventName: String,
    @ColumnInfo(name = "LocalDate")
    var eventLocalDate: String,
    @ColumnInfo(name = "LocalTime")
    var eventLocalTime: String,
    @ColumnInfo(name = "EventInfo")
    var eventInfo: String,
    @ColumnInfo(name = "PageNumber")
    var pageNumber: String
)