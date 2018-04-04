package com.nooblabs.resturaall.v3.controllers

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nooblabs.resturaall.R
import com.nooblabs.resturaall.v3.dataproviders.models.Restaurant
import kotlinx.android.synthetic.main.restaurant_list_item2.view.*

class RestaurantListAdapter(var context: Context): RecyclerView.Adapter<RestaurantListAdapter.VH>() {
    class VH(view: View): RecyclerView.ViewHolder(view)

    var restList =  ArrayList<Restaurant>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        Log.d("adapter","viewCreated")
        val view = LayoutInflater.from(context).inflate(R.layout.restaurant_list_item2, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return restList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val res = restList[position]
        holder.itemView.res_name.text = res.name
        holder.itemView.res_address.text = res.address
        holder.itemView.res_rating.text = res.rating.toString()
    }
}
