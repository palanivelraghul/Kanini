package com.task.kaninieventvenu.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.task.kaninieventvenu.room.dao.EventVenueDAO
import com.task.kaninieventvenu.room.entity.EventEntity
import com.task.kaninieventvenu.room.entity.VenueEntity
import com.task.kaninieventvenu.utils.AppConstant

@Database(entities = [EventEntity::class, VenueEntity::class], version = 1, exportSchema = false)
abstract class KaniniEventDatabase : RoomDatabase() {

    abstract fun eventVenueDao(): EventVenueDAO

    companion object {
        var instance: KaniniEventDatabase? = null

        fun getKaniniEventDatabase(context: Context): KaniniEventDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    KaniniEventDatabase::class.java,
                    AppConstant.mDATABASE_NAME
                ).build()
            }
            return instance as KaniniEventDatabase
        }
    }
}