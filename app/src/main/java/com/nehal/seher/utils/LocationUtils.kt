package com.nehal.seher.utils

import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.google.type.LatLng
import com.nehal.seher.SeherApplication
import java.util.*


object LocationUtils {
    fun getCityNameFromLatLong(lat: Double, lon: Double): String {
        val locale = Locale("en")
        var cityName: String
        val geoCoder = Geocoder(SeherApplication.instance, locale)
        val addresses: List<Address> = geoCoder.getFromLocation(lat, lon, 1)
        cityName = addresses[0].locality

        if (cityName == null) {
            cityName = addresses[0].adminArea
        }

        return cityName
    }

    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lng1, lat2, lng2, results)
        return results[0]
    }

    fun distanceText(distance: Float): String {
        val distanceString: String

        if (distance < 1000)
            if (distance < 1)
                distanceString = String.format(Locale.US, "%dm", 1)
            else
                distanceString = String.format(Locale.US, "%dm", Math.round(distance))
        else if (distance > 10000)
            if (distance < 1000000)
                distanceString = String.format(Locale.US, "%dkm", Math.round(distance / 1000))
            else
                distanceString = String.format(Locale.US, "%dkm", Math.round(distance / 1000))
        else
            distanceString = String.format(Locale.US, "%.2fkm", distance / 1000)

        return distanceString
    }
}