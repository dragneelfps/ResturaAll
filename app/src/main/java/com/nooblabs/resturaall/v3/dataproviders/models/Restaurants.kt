package com.nooblabs.resturaall.v3.dataproviders.models

data class Restaurant(var name: String, var address: String, var rating: Double){
    override fun toString(): String {
        return "$name/$address/$rating"
    }
}