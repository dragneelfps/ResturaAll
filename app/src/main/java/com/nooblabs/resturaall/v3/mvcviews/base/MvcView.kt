package com.nooblabs.resturaall.v3.mvcviews.base

import android.os.Bundle
import android.view.View

interface MvcView {


    fun getRootView(): View

    fun getViewState(): Bundle?
}