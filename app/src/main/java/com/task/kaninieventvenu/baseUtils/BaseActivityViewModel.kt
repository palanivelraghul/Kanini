package com.task.kaninieventvenu.baseUtils

abstract class BaseActivityViewModel<VM : AppBaseViewModel> : BaseActivity() {

    abstract fun onCreateViewModel(): VM

    open fun <T : AppBaseViewModel?> onRetry(viewModel: T) {
        //findViewById(R.id.btn_api_retry).setOnClickListener(View.OnClickListener { viewModel?.onRetryClickListener() })
        //findViewById(R.id.btn_network_reload).setOnClickListener(View.OnClickListener { viewModel?.onRetryClickListener() })
    }
}