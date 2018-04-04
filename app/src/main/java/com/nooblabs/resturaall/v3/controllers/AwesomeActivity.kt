package com.nooblabs.resturaall.v3.controllers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.nooblabs.resturaall.logd
import com.nooblabs.resturaall.v3.dataproviders.RestaurantProvider
import com.nooblabs.resturaall.v3.dataproviders.RestaurantProviderImp
import com.nooblabs.resturaall.v3.dataproviders.models.Restaurant
import com.nooblabs.resturaall.v3.mvcviews.NearbyPlacesViewMvcImp
import com.nooblabs.resturaall.v3.mvcviews.base.NearbyPlacesViewMvc

class AwesomeActivity: AppCompatActivity(), NearbyPlacesViewMvc.LocationSelectedListener, RestaurantProvider.LoadFinished {

    private lateinit var mNearbyPlacesView: NearbyPlacesViewMvc

    private lateinit var mRestaurantProvider: RestaurantProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNearbyPlacesView = NearbyPlacesViewMvcImp(this, layoutInflater, null)
        setContentView(mNearbyPlacesView.getRootView())
        mNearbyPlacesView.setUpWidgets(fragmentManager, this)
        mRestaurantProvider = RestaurantProviderImp()
        mRestaurantProvider.setOnLoadListener(this)
    }

    override fun onLocationSelected(place: Place?) {
        logd("Selected place: ${place?.latLng}")
        place?.let {
            val bundle = Bundle()
            bundle.putString("location", "${it.latLng.latitude},${it.latLng.longitude}")
            bundle.putString("radius", "2000")
//            bundle.putString("keyword","yolo")
            mRestaurantProvider.initLoading(bundle)
        }

    }



    override fun onError(status: Status?) {
        logd("Error occurred: $status")
    }

    override fun onLoad(list: ArrayList<Restaurant>) {
        val adapter = RestaurantListAdapter(applicationContext)
        adapter.restList = list
        mNearbyPlacesView.updateAdapter(adapter, applicationContext)
    }



}