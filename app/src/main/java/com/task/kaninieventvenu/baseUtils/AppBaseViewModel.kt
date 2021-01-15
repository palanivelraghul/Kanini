package com.task.kaninieventvenu.baseUtils

import androidx.lifecycle.ViewModel

abstract class AppBaseViewModel : ViewModel(), APICallBack {

    open fun onRetryClickListener() {
        // Todo Override this method where you want retry.
    }

    override fun onAPIFailure(failureResponse: String?, failureTransactionId: Int) {
        super.onAPIFailure(failureResponse, failureTransactionId)
    }

    override fun onAPISuccess(
        successResponse: String,
        successTransactionId: Int
    ) {
        super.onAPISuccess(successResponse, successTransactionId)
    }

    override fun initiateAPICall(apiTransactionId: Int) {
        super.initiateAPICall(apiTransactionId)
    }


}