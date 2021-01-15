package com.task.kaninieventvenu.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.task.kaninieventvenu.baseUtils.AppBaseViewModel
import com.task.kaninieventvenu.model.EventDetailsResponse
import com.task.kaninieventvenu.model.KaniniEvents
import com.task.kaninieventvenu.retrofit.ServiceRepository
import com.task.kaninieventvenu.room.KaniniEventDatabase
import com.task.kaninieventvenu.room.entity.EventEntity
import com.task.kaninieventvenu.room.entity.VenueEntity
import com.task.kaninieventvenu.utils.AppConstant
import kotlinx.coroutines.*

class EventsListActivityViewModel : AppBaseViewModel() {

    private var pageNumber = 0
    private var pageSize = 20

    val mEventDetailsListLiveData = MutableLiveData<String>()
    var overallEventList = mutableListOf<EventEntity>()

    private lateinit var mCallBack: EventsListActivityViewModelCallBack
    private lateinit var kaniniEventDatabase: KaniniEventDatabase

    fun init(
        callBackList: EventsListActivityViewModelCallBack,
        kaniniEventDatabase: KaniniEventDatabase
    ) {
        this.mCallBack = callBackList
        this.kaniniEventDatabase = kaniniEventDatabase
        mCallBack.showLoader()
        initiateAPICall(ServiceRepository.GET_EVENT_DETAILS)
    }

    override fun initiateAPICall(apiTransactionId: Int) {
        super.initiateAPICall(apiTransactionId)
        if (mCallBack.isInternetConnectionSuccess()) {
            viewModelScope.launch {
                ServiceRepository.getEventDetails(
                    this@EventsListActivityViewModel,
                    AppConstant.API_KEY_VALUE,
                    pageNumber.toString()
                )
            }
        } else {
            initiateDataCallFromLocalDB()
        }
    }

    private fun initiateDataCallFromLocalDB() {
        getEventListFromDB()
    }

    override fun onAPISuccess(successResponse: String, successTransactionId: Int) {
        super.onAPISuccess(successResponse, successTransactionId)
        mEventDetailsListLiveData.value = successResponse
    }

    override fun onAPIFailure(failureResponse: String?, failureTransactionId: Int) {
        super.onAPIFailure(failureResponse, failureTransactionId)
        mCallBack.showAPIError()
        mCallBack.dismissLoader()
    }

    fun initiateEventListFromServer(eventDetailsListResponse: String?) {
        if (eventDetailsListResponse!!.isNotEmpty()) {
            val eventDetailsResponse =
                Gson().fromJson(eventDetailsListResponse, EventDetailsResponse::class.java)
            if (eventDetailsResponse?.mEventDetailsEmbeddedList != null && eventDetailsResponse.mEventDetailsEmbeddedList!!.mEventList != null && eventDetailsResponse.mEventDetailsEmbeddedList!!.mEventList!!.isNotEmpty()) {
                insertDataFromServerToLocalDB(eventDetailsResponse.mEventDetailsEmbeddedList!!.mEventList!!)
            } else {
                initiateDataCallFromLocalDB()
            }
        } else {
            initiateDataCallFromLocalDB()
        }
    }

    private fun insertDataFromServerToLocalDB(mEventList: List<KaniniEvents>) {
        CoroutineScope(Dispatchers.IO).launch {
            val job = async {
                for (eventDetails in mEventList) {
                    val eventLocalDate =
                        if (eventDetails.eventDates?.eventStart?.localDate!!.isNotEmpty()) {
                            eventDetails.eventDates!!.eventStart!!.localDate!!
                        } else {
                            " "
                        }
                    val eventLocalTime =
                        if (eventDetails.eventDates?.eventStart?.localTime!!.isNotEmpty()) {
                            eventDetails.eventDates!!.eventStart!!.localTime!!
                        } else {
                            " "
                        }
                    kaniniEventDatabase.eventVenueDao().insertEventDetails(
                        EventEntity(
                            eventDetails.id!!,
                            eventDetails.name!!,
                            eventLocalDate,
                            eventLocalTime,
                            if (eventDetails.info.isNullOrEmpty()) " " else eventDetails.info!!,
                            pageNumber.toString()
                        )
                    )
                    if (eventDetails.mEventEmbeddedDetails != null && eventDetails.mEventEmbeddedDetails?.venues != null && eventDetails.mEventEmbeddedDetails?.venues!!.isNotEmpty()) {
                        for (venueDetails in eventDetails.mEventEmbeddedDetails?.venues!!) {
                            var venueEntity = VenueEntity(
                                venueDetails.id!!,
                                eventDetails.id!!,
                                venueDetails.name!!,
                                if (venueDetails.city != null && venueDetails.city!!.name!!.isNotEmpty()) venueDetails.city!!.name!! else " ",
                                if (venueDetails.state != null && venueDetails.state!!.name!!.isNotEmpty()) venueDetails.state!!.name!! else " ",
                                if (venueDetails.country != null && venueDetails.country!!.name!!.isNotEmpty()) venueDetails.country!!.name!! else " ",
                                if (venueDetails.location != null && venueDetails.location!!.longitude!!.isNotEmpty()) venueDetails.location!!.longitude!! else " ",
                                if (venueDetails.location != null && venueDetails.location!!.latitude!!.isNotEmpty()) venueDetails.location!!.latitude!! else " ",
                            )
                            kaniniEventDatabase.eventVenueDao().insertVenueDetails(venueEntity)
                            Log.e(TAG, "insertDataFromServerToLocalDB: #############" + venueEntity)
                        }
                    }
                }
            }
            withContext(Dispatchers.Main) {
                job.await()
                getEventListFromDB();
            }
        }
    }

    private fun getEventListFromDB() {
        var eventDetailsList = listOf<EventEntity>()
        CoroutineScope(Dispatchers.IO).launch {
            var job = async {
                eventDetailsList =
                    kaniniEventDatabase.eventVenueDao().getEventDetailsList(pageNumber.toString())
            }
            withContext(Dispatchers.Main) {
                job.await()
                if (!eventDetailsList.isNullOrEmpty()) {
                    if (overallEventList.isEmpty()) {
                        overallEventList.addAll(eventDetailsList.toMutableList())
                    }
                    mCallBack.updateListWithDataInAdapter(overallEventList, eventDetailsList)
                } else {
                    mCallBack.dismissLoader()
                    mCallBack.hideLazyLoader()
                }
            }
        }
    }

    fun getRecyclerViewPagination(layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener? {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + firstVisibleItem >= totalItemCount && firstVisibleItem >= 0 && totalItemCount >= pageSize) {
                    pageNumber += 1
                    mCallBack.showLazyLoader()
                    initiateAPICall(ServiceRepository.GET_EVENT_DETAILS)
                }
            }
        }
    }

    interface EventsListActivityViewModelCallBack {
        fun showToastMessage(message: String)
        fun isInternetConnectionSuccess(): Boolean
        fun showLoader()
        fun dismissLoader()
        fun showNetworkError()
        fun showAPIError()
        fun showLazyLoader()
        fun hideLazyLoader()
        fun updateListWithDataInAdapter(
            overallEventList: MutableList<EventEntity>,
            fetchedEventDetailsList: List<EventEntity>
        )

        fun onEventItemClick(event: EventEntity)
    }

}
