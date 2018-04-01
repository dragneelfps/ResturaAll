package com.nooblabs.resturaall.v3.mvcviews.base

import android.app.FragmentManager
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.OnMapReadyCallback

interface NearbyPlacesViewMvc: MvcView, OnMapReadyCallback {

    fun setUpWidgets(fragmentManager: FragmentManager, locationSelectedListener: LocationSelectedListener?)

    fun setLocationSelectedListener(listener: LocationSelectedListener?)

    interface LocationSelectedListener{
        fun onLocationSelected(place: Place?)
        fun onError(status: Status?)
    }

}