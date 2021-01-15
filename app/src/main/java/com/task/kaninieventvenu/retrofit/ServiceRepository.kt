package com.task.kaninieventvenu.retrofit

import com.google.gson.Gson
import com.task.kaninieventvenu.baseUtils.AppBaseViewModel


object ServiceRepository {

    private const val BASE_URL = "https://app.ticketmaster.com/discovery/v2/"

    const val GET_EVENT_DETAILS = 1000001

    suspend fun getEventDetails(viewModel: AppBaseViewModel, apiKey: String, pageNo: String) {
        val apiClient = APIClient.getClient(BASE_URL)?.create(RetrofitInterface::class.java)
        val response = apiClient?.getEventDetails(apiKey, pageNo)
        if (response?.isSuccessful!! && response.body() != null) {
            viewModel.onAPISuccess(Gson().toJson(response.body()!!), GET_EVENT_DETAILS)
        } else {
            viewModel.onAPIFailure(null, GET_EVENT_DETAILS)
        }
    }


}