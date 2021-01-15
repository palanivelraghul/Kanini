package com.task.kaninieventvenu.baseUtils


interface APICallBack {
    fun initiateAPICall(apiTransactionId: Int) {}

    fun onAPISuccess(successResponse: String, successTransactionId: Int) {}

    fun onAPIFailure(failureResponse: String?, failureTransactionId: Int) {}
}