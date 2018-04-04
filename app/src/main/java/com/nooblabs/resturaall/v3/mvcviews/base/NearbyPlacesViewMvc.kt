package com.nooblabs.resturaall.v3.mvcviews.base

import android.app.FragmentManager
import android.content.Context
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.OnMapReadyCallback
import com.nooblabs.resturaall.v3.controllers.AwesomeActivity
import com.nooblabs.resturaall.v3.controllers.RestaurantListAdapter

interface NearbyPlacesViewMvc: MvcView, OnMapReadyCallback {

    fun setUpWidgets(fragmentManager: FragmentManager, locationSelectedListener: LocationSelectedListener?)

    fun setLocationSelectedListener(listener: LocationSelectedListener?)
    fun updateAdapter(adapter: RestaurantListAdapter, context: Context)

    interface LocationSelectedListener{
        fun onLocationSelected(place: Place?)
        fun onError(status: Status?)
    }

}