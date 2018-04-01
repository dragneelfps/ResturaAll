package com.nooblabs.resturaall

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast(str: String){
    Toast.makeText(this, str, Toast.LENGTH_LONG).show()
}

fun getApiKey() = "AIzaSyD3YNiQnbjx3oMQFgnI6DhxIQ-VXK0wl0Q"

fun getMapsApiKey() = "AIzaSyDaInjfDAnt21-hZttExCvJUo2p0hc2p_o"

fun Any.getTAG(): String {
    val elements = Thread.currentThread().stackTrace

    var position = 0
    for (i in elements.indices) {
        if (elements[i].fileName.contains(this.javaClass.simpleName) && !elements[i + 1].fileName.contains(this.javaClass.simpleName)) {
            position = i
            break
        }
    }

    val element = elements[position]
    val className = element.fileName.replace(".java", "")
    return "[" + className + "](" + element.methodName + ":" + element.lineNumber + ")"
}

fun Any.logd(message: String){
    Log.d(this::class.java.simpleName, message)
}