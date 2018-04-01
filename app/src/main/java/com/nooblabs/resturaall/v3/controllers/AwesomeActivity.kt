package com.nooblabs.resturaall.v3.controllers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.nooblabs.resturaall.logd
import com.nooblabs.resturaall.v3.mvcviews.base.NearbyPlacesViewMvc
import com.nooblabs.resturaall.v3.mvcviews.NearbyPlacesViewMvcImp

class AwesomeActivity: AppCompatActivity(), NearbyPlacesViewMvc.LocationSelectedListener {
    private lateinit var mNearbyPlacesView: NearbyPlacesViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNearbyPlacesView = NearbyPlacesViewMvcImp(this, layoutInflater, null)
        setContentView(mNearbyPlacesView.getRootView())
        mNearbyPlacesView.setUpWidgets(fragmentManager, this)
    }

    override fun onLocationSelected(place: Place?) {
        logd("Selected place: ${place?.latLng}")
    }

    override fun onError(status: Status?) {
        logd("Error occurred: $status")
    }

}