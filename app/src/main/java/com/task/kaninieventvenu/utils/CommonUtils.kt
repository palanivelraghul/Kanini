package com.task.kaninieventvenu.utils

import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper


object CommonUtils {

    /*fun loadUrlImage(context: Context?, imageView: ImageView, imageUrl: String?) {
        Glide.with(context!!).load(imageUrl).thumbnail(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_status_none)
            .error(Glide.with(imageView).load(R.drawable.ic_status_none)).into(imageView)
    }*/

    fun setSnapHelperProperties(recyclerView: RecyclerView) {
        val snapHelper: SnapHelper = PagerSnapHelper()
        recyclerView.onFlingListener = null
        snapHelper.attachToRecyclerView(recyclerView)
    }

}