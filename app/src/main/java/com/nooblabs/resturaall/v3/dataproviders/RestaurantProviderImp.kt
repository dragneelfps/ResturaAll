package com.nooblabs.resturaall.v3.dataproviders

import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import com.nooblabs.resturaall.getApiKey
import com.nooblabs.resturaall.v3.dataproviders.models.Params
import com.nooblabs.resturaall.v3.dataproviders.models.Restaurant
import com.nooblabs.resturaall.v3.dataproviders.models.paramsFromBundle
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.net.URLDecoder

class RestaurantProviderImp: RestaurantProvider{


    private var mParams: Bundle? = null
    private var mOnLoadListener: RestaurantProvider.LoadFinished? = null

    override fun setOnLoadListener(listener: RestaurantProvider.LoadFinished?) {
        mOnLoadListener = listener
    }


    override fun initLoading(bundle : Bundle){
        val params = paramsFromBundle(bundle)
        GetRestaurantsTask().execute(params)
    }

    inner class GetRestaurantsTask: AsyncTask<Params, Void, ArrayList<Restaurant>>(){
        override fun doInBackground(vararg params: Params?): ArrayList<Restaurant> {
            val pr = params[0]!!
            val client = OkHttpClient()
            val loc = URLDecoder.decode(pr.location, "UTF-8").toString()
            var urlTmp = Uri.Builder()
                    .scheme("https")
                    .authority("maps.googleapis.com")
                    .appendPath("maps")
                    .appendPath("api")
                    .appendPath("place")
                    .appendPath("nearbysearch")
                    .appendPath("json")
                    .appendQueryParameter("key", getApiKey())
                    .appendQueryParameter("location", loc)
                    .appendQueryParameter("type","restaurant")
//                    .appendQueryParameter("rankby","distance")
                    .appendQueryParameter("radius",pr.radius)
            pr.keyword?.let {
                urlTmp = urlTmp.appendQueryParameter("keyword",it)
            }
            val url = urlTmp.build()

            Log.d("uri",url.toString())

            val req = Request.Builder()
                    .url(url.toString())
                    .build()

            val responses = client.newCall(req).execute()

            val jsonData = responses.body()?.string()
            jsonData?.let{
                val jsonObj = JSONObject(it)
                val results = jsonObj.getJSONArray("results")
                val rests = ArrayList<Restaurant>()
                try {
                    for (i in 0..(results.length() - 1)) {
                        val r = results.getJSONObject(i)
                        val name = r.getString("name")
                        val address = r.getString("vicinity")
                        val rating = r.getDouble("rating")
                        rests.add(Restaurant(name, address, rating))
                    }
                }catch (e: JSONException){
                    return rests
                }
                return rests
            }

            return arrayListOf()
        }

        override fun onPostExecute(result: ArrayList<Restaurant>?) {
            result?.forEach {
                Log.d("restaurants", it.toString())
            }
            if(result != null)
                mOnLoadListener?.onLoad(result)
        }
    }

}