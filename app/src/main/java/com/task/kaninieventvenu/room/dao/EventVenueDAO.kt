package com.task.kaninieventvenu.room.dao

import androidx.room.*
import com.task.kaninieventvenu.room.entity.EventEntity
import com.task.kaninieventvenu.room.entity.VenueEntity

@Dao
interface EventVenueDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventDetails(eventEntity: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVenueDetails(venueEntity: VenueEntity)

    @Query("Select * from EventTable where EventId=:eventId")
    suspend fun getEventDetails(eventId: String): EventEntity

    @Query("Select * from EventTable where PageNumber=:pageNumber")
    suspend fun getEventDetailsList(pageNumber: String): List<EventEntity>

    @Query("Select * from VenueTable where VenueId=:venueId")
    suspend fun getVenueDetails(venueId: String): VenueEntity

    @Query("SELECT * FROM VenueTable WHERE VenueEventId = :eventId")
    fun getEventVenueList(eventId: String): List<VenueEntity>

    /*@Transaction
    @Query("Select * from EventTable")
    suspend fun getEventVenueList(): List<VenueEntity>*/


}