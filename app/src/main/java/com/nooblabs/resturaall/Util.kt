package com.nooblabs.resturaall

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast(str: String){
    Toast.makeText(this, str, Toast.LENGTH_LONG).show()
}

fun getApiKey() = "AIzaSyD3YNiQnbjx3oMQFgnI6DhxIQ-VXK0wl0Q"


fun Any.logd(message: String){
    Log.d(this::class.java.simpleName, message)
}