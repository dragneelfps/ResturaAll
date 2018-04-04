package com.nooblabs.resturaall.v3.dataproviders.models

import android.os.Bundle

fun paramsFromBundle(bundle: Bundle): Params {
    val location = bundle.getString("location") ?: throw Exception("Location should be specified")
    val radius = bundle.getString("radius") ?: throw Exception("Radius must be specified")
    val keyword = bundle.getString("keyword")
    return Params(location, radius, keyword)

}

data class Params(var location: String, var radius: String, var keyword: String?)
