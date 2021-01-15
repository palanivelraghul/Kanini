package com.task.kaninieventvenu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.kaninieventvenu.databinding.ItemEventListBinding
import com.task.kaninieventvenu.room.entity.EventEntity
import com.task.kaninieventvenu.viewModel.EventsListActivityViewModel

class EventListAdapter(
    var mCallBack: EventsListActivityViewModel.EventsListActivityViewModelCallBack,
    var overallEventList: MutableList<EventEntity>
) : RecyclerView.Adapter<EventListAdapter.EventListAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListAdapterViewHolder {
        var mBinding =
            ItemEventListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventListAdapterViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: EventListAdapterViewHolder, position: Int) {
        holder.onBindData(mCallBack, overallEventList)
    }

    override fun getItemCount(): Int {
        return overallEventList.size
    }

    fun updateEventDataList(
        overallEventList: MutableList<EventEntity>,
        updatedList: List<EventEntity>
    ) {
        var previousSize = overallEventList.size
        this.overallEventList.addAll(updatedList.toMutableList())
        notifyItemRangeInserted(previousSize, updatedList.size)
    }

    class EventListAdapterViewHolder(var mBinding: ItemEventListBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun onBindData(
            mCallBack: EventsListActivityViewModel.EventsListActivityViewModelCallBack,
            overallEventList: MutableList<EventEntity>
        ) {
            mBinding.callBack = mCallBack
            mBinding.position = adapterPosition
            mBinding.event = overallEventList[adapterPosition]
        }


    }

}
