package com.nehal.seher.utils

import android.location.Address
import android.location.Geocoder
import com.nehal.seher.SeherApplication
import java.util.*


object LocationUtils {
    fun getCityNameFromLatLong(lat:Double, lon:Double):String{
        val locale = Locale("en")
        var cityName:String
        val geoCoder = Geocoder(SeherApplication.instance, locale)
        val addresses: List<Address> = geoCoder.getFromLocation(lat, lon, 1)
        cityName = addresses[0].locality

        if (cityName == null) {
            cityName = addresses[0].adminArea
        }

        return cityName
    }
}