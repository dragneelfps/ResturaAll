package com.nooblabs.resturaall.v3.dataproviders

import android.os.Bundle
import com.nooblabs.resturaall.v3.dataproviders.models.Restaurant

interface RestaurantProvider {

    interface LoadFinished{
        fun onLoad(list: ArrayList<Restaurant>)
    }

    fun initLoading(bundle: Bundle)

    fun setOnLoadListener(listener: LoadFinished?)

}