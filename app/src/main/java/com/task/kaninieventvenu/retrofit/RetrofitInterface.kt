package com.task.kaninieventvenu.retrofit

import com.task.kaninieventvenu.model.EventDetailsResponse
import com.task.kaninieventvenu.utils.AppConstant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET(ServicePathConstant.GET_EVENT_DETAILS)
    suspend fun getEventDetails(
        @Query(AppConstant.API_KEY) apiKey: String,
        @Query(AppConstant.RESPONSE_PAGE_NUMBER) pageNo: String
    ): Response<EventDetailsResponse>?

}