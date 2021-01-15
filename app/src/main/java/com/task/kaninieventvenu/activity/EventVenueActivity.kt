package com.task.kaninieventvenu.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.task.kaninieventvenu.R
import com.task.kaninieventvenu.baseUtils.BaseActivityViewModel
import com.task.kaninieventvenu.databinding.ActivityEventVenueDetailsBinding
import com.task.kaninieventvenu.room.KaniniEventDatabase
import com.task.kaninieventvenu.room.entity.VenueEntity
import com.task.kaninieventvenu.utils.AppConstant
import com.task.kaninieventvenu.viewModel.EventVenuActivityViewModel

class EventVenueActivity : BaseActivityViewModel<EventVenuActivityViewModel>(), OnMapReadyCallback,
    EventVenuActivityViewModel.EventVenueActivityViewmodelCallBack {

    private lateinit var mMap: GoogleMap
    private lateinit var mViewModel: EventVenuActivityViewModel
    private lateinit var mKaniniEventDB: KaniniEventDatabase
    private lateinit var mBinding: ActivityEventVenueDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_venue_details)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        onCreateViewModel()
    }

    override fun onCreateViewModel(): EventVenuActivityViewModel {
        mViewModel = ViewModelProvider(this).get(EventVenuActivityViewModel::class.java)
        setUpToolbar()
        initializeDB()
        mViewModel.initiateData(this, intent, mKaniniEventDB)
        return mViewModel
    }

    private fun setUpToolbar() {
        setSupportActionBar(mBinding.toolBar)
        supportActionBar!!.title = intent.getStringExtra(AppConstant.EVENT_NAME)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        mBinding.toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initializeDB() {
        mKaniniEventDB = KaniniEventDatabase.getKaniniEventDatabase(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        /*// Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
    }

    override fun updateMapWithDetails(venueDetailsList: List<VenueEntity>) {
        for (venueDetails in venueDetailsList) {
            val markerPoint =
                LatLng(venueDetails.longitude.toDouble(), venueDetails.latitude.toDouble())
            mMap.addMarker(
                MarkerOptions().position(markerPoint)
                    .title(venueDetails.city + "\n" + venueDetails.state + "\n" + venueDetails.country)
                    .visible(true)
            )
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPoint, 5.0f))
        }
    }
}