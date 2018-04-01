package com.nooblabs.resturaall.v2

import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.nooblabs.resturaall.R
import kotlinx.android.synthetic.main.abs_slidingup_layout.*
import kotlinx.android.synthetic.main.nearby_locations_slidingup_layout.view.*

abstract class AbsSlidingUpActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.abs_slidingup_layout)
    }

    fun setSlidingUpContent(@LayoutRes res: Int){
        val slidingUpView = layoutInflater.inflate(res, null)
        slidingup_content.addView(slidingUpView)
    }

    fun setMainContent(@LayoutRes res: Int){
        main_content.addView(layoutInflater.inflate(res, null))
    }

}