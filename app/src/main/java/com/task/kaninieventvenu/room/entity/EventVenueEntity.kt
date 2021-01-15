package com.task.kaninieventvenu.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class EventVenueEntity(
    @Embedded val event: EventEntity,
    @Relation(
        parentColumn = "EventId",
        entityColumn = "FK_EventId"
    )
    val venueDetails: List<VenueEntity>

)
