package com.nooblabs.resturaall.v3.mvcviews

import android.content.Context
import android.os.Bundle
import android.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.nooblabs.resturaall.R
import com.nooblabs.resturaall.logd
import com.nooblabs.resturaall.v3.controllers.RestaurantListAdapter
import com.nooblabs.resturaall.v3.mvcviews.base.NearbyPlacesViewMvc
import kotlinx.android.synthetic.main.abs_slidingup_layout.view.*
import kotlinx.android.synthetic.main.nearby_places_slideup_content_layout.view.*

class NearbyPlacesViewMvcImp(context: Context, layoutInflater: LayoutInflater, container: ViewGroup?) : NearbyPlacesViewMvc {

    private var mMap: GoogleMap? = null
    private var mLocationSelectedListener: NearbyPlacesViewMvc.LocationSelectedListener? = null
    private var mRootView: View
    private var mSearchBox : PlaceAutocompleteFragment? = null
    private var adapter: RestaurantListAdapter? = null
    init {
        mRootView = layoutInflater.inflate(R.layout.abs_slidingup_layout, container)
        mRootView.main_content.addView(layoutInflater.inflate(R.layout.activity_nearby_places2, null))
        mRootView.slidingup_content.addView(layoutInflater.inflate(R.layout.nearby_places_slideup_content_layout, null))
    }

    override fun setLocationSelectedListener(listener: NearbyPlacesViewMvc.LocationSelectedListener?) {
        mLocationSelectedListener = listener
    }

    override fun setUpWidgets(fragmentManager: FragmentManager, locationSelectedListener: NearbyPlacesViewMvc.LocationSelectedListener?){
        val mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)
        mLocationSelectedListener = locationSelectedListener
        mSearchBox = fragmentManager.findFragmentById(R.id.place_autocomplete_fragment2) as PlaceAutocompleteFragment
        mSearchBox?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place?) {
                onLocationSelected(p0)
                mLocationSelectedListener?.onLocationSelected(p0)
            }

            override fun onError(p0: Status?) {
                mLocationSelectedListener?.onError(p0)
            }
        })
    }

    fun onLocationSelected(place: Place?){
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(place?.latLng, 10f))
    }



    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0
        updateLocationUi(true)
    }

    fun updateLocationUi(locationPermission: Boolean){
        mMap?.let { mMap ->
            try{
                if(locationPermission){
                    mMap.isMyLocationEnabled = true
                    mMap.uiSettings.isMyLocationButtonEnabled = true
                }else{
                    mMap.isMyLocationEnabled = false
                    mMap.uiSettings.isMyLocationButtonEnabled = false
                }
            }catch (e: SecurityException){
                logd("Error occurred: ${e.message}")
            }
        }
    }

    override fun getRootView(): View {
        return mRootView
    }

    override fun getViewState(): Bundle? {
        return null
    }

    override fun updateAdapter(adapter: RestaurantListAdapter, context: Context) {
        Log.d("app","Updating adapter")
        mRootView.nearby_restaurant_list.layoutManager = LinearLayoutManager(context)
        mRootView.nearby_restaurant_list.adapter = adapter
    }
}