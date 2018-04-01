package com.nooblabs.resturaall.v2

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nooblabs.resturaall.R
import kotlinx.android.synthetic.main.abs_slidingup_layout.*

class NearbyPlacesActivity : AbsSlidingUpActivity(), OnMapReadyCallback {

    companion object {
        val TAG = NearbyPlacesActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setMainContent(R.layout.activity_nearby_places2)
        setSlidingUpContent(R.layout.nearby_places_slideup_content_layout)


        getLocationPermission()

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val autoCompleteFragment = fragmentManager.findFragmentById(R.id.place_autocomplete_fragment2) as PlaceAutocompleteFragment
        autoCompleteFragment.setOnPlaceSelectedListener(object: PlaceSelectionListener{
            override fun onPlaceSelected(p0: Place?) {
                setMapCustomLocation(p0)
            }

            override fun onError(p0: Status?) {
                Snackbar.make(root_layout, "Error occurred: $p0", Snackbar.LENGTH_SHORT)
            }
        })
    }

    private var mLocationPermissionGranted: Boolean = false

    fun getLocationPermission(){
        if(ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mLocationPermissionGranted = true
        }else{
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    123)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 123){
            mLocationPermissionGranted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        updateLocationUI()
    }

    private var mMap: GoogleMap? = null

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0
        updateLocationUI()
        getDeviceLocation()
    }

    private fun updateLocationUI(){
        mMap?.let{mMap ->
            try{
                if(mLocationPermissionGranted){
                    mMap.isMyLocationEnabled = true
                    mMap.uiSettings.isMyLocationButtonEnabled = true
                    mMap.uiSettings.isZoomControlsEnabled = true
                }else{
                    mMap.isMyLocationEnabled = false
                    mMap.uiSettings.isMyLocationButtonEnabled = false
                    mLastKnownLocation = null
                    getLocationPermission()
                }
            }catch (e: SecurityException){
                Log.e("Exception: %s", e.message)
            }
        }
    }

    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    private var mLastKnownLocation: Location? = null

    private val DEFAULT_ZOOM: Float = 15f

    private val mDefaultLocation: LatLng = LatLng(69.0, 69.0)

    private fun getDeviceLocation(){
        try{
            if(mLocationPermissionGranted){
                val locationResult = mFusedLocationProviderClient.getLastLocation()
                locationResult.addOnCompleteListener {
                    if(it.isSuccessful){
                        mLastKnownLocation = it.result
                        mLastKnownLocation?.run {
                            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    LatLng(latitude, longitude), DEFAULT_ZOOM
                            ))
                        }

                    }else{
                        Log.d(TAG, "Current Location is null. Using Defaults.")
                        Log.e(TAG, "Exception : ${it.exception}")
                        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                mDefaultLocation, DEFAULT_ZOOM
                        ))
                    }
                }
            }
        }catch (e: SecurityException){
            Log.e("Exception : %s", e.message)
        }
    }

    private fun setMapCustomLocation(place: Place?){
        place?.let {
            Log.d(TAG,"setMapCustomLocation")
            try{
                if(mLocationPermissionGranted){
                    mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            it.latLng, DEFAULT_ZOOM
                    ))
                    mMap?.addMarker(MarkerOptions().position(it.latLng))
                }else{
                    getLocationPermission()
                }
            }catch (e: SecurityException){
                Log.e("Exception : %s", e.message)
            }
        }
    }

}
