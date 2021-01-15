package com.task.kaninieventvenu.viewModel

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.task.kaninieventvenu.baseUtils.AppBaseViewModel
import com.task.kaninieventvenu.room.KaniniEventDatabase
import com.task.kaninieventvenu.room.entity.EventEntity
import com.task.kaninieventvenu.room.entity.VenueEntity
import com.task.kaninieventvenu.utils.AppConstant
import kotlinx.coroutines.*

class EventVenuActivityViewModel : AppBaseViewModel() {

    private lateinit var mCallBack: EventVenueActivityViewmodelCallBack;
    private lateinit var intent: Intent
    private lateinit var mKaniniEventDB: KaniniEventDatabase

    fun initiateData(
        mCallBack: EventVenueActivityViewmodelCallBack,
        intent: Intent?,
        mKaniniEventDB: KaniniEventDatabase
    ) {
        this.intent = intent!!
        this.mCallBack = mCallBack
        this.mKaniniEventDB = mKaniniEventDB
        initiateDataCallFRomDB(intent.getStringExtra(AppConstant.EVENT_ID))
    }

    private fun initiateDataCallFRomDB(eventId: String?) {
        var venueDetailsList = listOf<VenueEntity>()
        var eventEntity: EventEntity? = null
        CoroutineScope(Dispatchers.IO).launch {
            val job = async {
                venueDetailsList = mKaniniEventDB.eventVenueDao().getEventVenueList(eventId!!)
                eventEntity = mKaniniEventDB.eventVenueDao().getEventDetails(eventId)
            }
            withContext(Dispatchers.Main) {
                job.await()
                mCallBack.updateMapWithDetails(venueDetailsList!!)
            }
        }
    }

    interface EventVenueActivityViewmodelCallBack {
        fun updateMapWithDetails(venueDetailsList: List<VenueEntity>)

    }

}
