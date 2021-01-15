package com.task.kaninieventvenu.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.kaninieventvenu.R
import com.task.kaninieventvenu.adapter.EventListAdapter
import com.task.kaninieventvenu.baseUtils.BaseActivityViewModel
import com.task.kaninieventvenu.databinding.ActivityEventListBinding
import com.task.kaninieventvenu.room.KaniniEventDatabase
import com.task.kaninieventvenu.room.entity.EventEntity
import com.task.kaninieventvenu.utils.AppConstant
import com.task.kaninieventvenu.utils.NetworkUtils
import com.task.kaninieventvenu.viewModel.EventsListActivityViewModel

class EventsListActivity : BaseActivityViewModel<EventsListActivityViewModel>(),
    EventsListActivityViewModel.EventsListActivityViewModelCallBack {

    private lateinit var mBinding: ActivityEventListBinding
    private lateinit var mViewModel: EventsListActivityViewModel
    private lateinit var mKaniniEventDB: KaniniEventDatabase
    private var mAdapter: EventListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_list)
        mBinding.viewModel = onCreateViewModel()
    }

    override fun onCreateViewModel(): EventsListActivityViewModel {
        mViewModel = ViewModelProvider(this).get(EventsListActivityViewModel::class.java)
        setUpToolbar()
        initializeDB()
        mViewModel.init(this, mKaniniEventDB)
        mViewModel.mEventDetailsListLiveData.observe(this, EventDetailsListObserver())
        return mViewModel
    }

    private fun setUpToolbar() {
        setSupportActionBar(mBinding.toolBar)
        supportActionBar!!.title = getString(R.string.text_events)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        mBinding.toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initializeDB() {
        mKaniniEventDB = KaniniEventDatabase.getKaniniEventDatabase(this)
    }

    override fun updateListWithDataInAdapter(
        overallEventList: MutableList<EventEntity>,
        fetchedEventDetailsList: List<EventEntity>
    ) {
        if (overallEventList.isNotEmpty()) {
            if (mAdapter == null) {
                mAdapter = EventListAdapter(this, overallEventList)
                mBinding.rvEventList.layoutManager = LinearLayoutManager(this)
                mBinding.rvEventList.adapter = mAdapter
                mBinding.rvEventList.addOnScrollListener(
                    mViewModel.getRecyclerViewPagination(
                        mBinding.rvEventList.layoutManager as LinearLayoutManager
                    )!!
                )
            } else {
                if (fetchedEventDetailsList.isNotEmpty()) {
                    mAdapter!!.updateEventDataList(overallEventList, fetchedEventDetailsList)
                }
            }
        }
        dismissMainProgressBar()
        hideLazyLoader()
    }

    override fun onEventItemClick(event: EventEntity) {
        val intent = Intent(this, EventVenueActivity::class.java)
        intent.putExtra(AppConstant.EVENT_ID, event.eventId)
        intent.putExtra(AppConstant.EVENT_NAME, event.eventName)
        startActivity(intent)
    }

    override fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun isInternetConnectionSuccess(): Boolean {
        return NetworkUtils.isConnected(this)
    }

    override fun showLoader() {
        showMainProgressBar(this)
    }

    override fun dismissLoader() {
        dismissMainProgressBar()
    }

    override fun showNetworkError() {
        TODO("Not yet implemented")
    }

    override fun showAPIError() {
        TODO("Not yet implemented")
    }

    override fun showLazyLoader() {
        mBinding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLazyLoader() {
        mBinding.progressBar.visibility = View.GONE
    }

    private inner class EventDetailsListObserver : Observer<String> {
        override fun onChanged(eventDetailsListResponse: String?) {
            hideLazyLoader()
            mViewModel.initiateEventListFromServer(eventDetailsListResponse)
            mBinding.executePendingBindings()
        }

    }

}


