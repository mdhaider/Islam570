package com.nehal.seher.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nehal.seher.model.Dua
import com.nehal.seher.model.DuaModel
import com.nehal.seher.model.Names
import com.nehal.seher.retrofit.ApiHelper
import com.nehal.seher.utils.FileUtils
import java.lang.reflect.Type


class HomeRepository(private val apiHelper: ApiHelper) {
    suspend fun getUrduPosters() = apiHelper.getUrduPosters()
    suspend fun getHijriFromGreg(date: String, adjustment:Int?) = apiHelper.getHijriFromGreg(date, adjustment)
    suspend fun getPrayerTimes(
        dateOrTimeStamp:String,
        latitude: Double,
        longitude: Double,
        method: String
    ) = apiHelper.getPrayerTimes(dateOrTimeStamp,latitude, longitude, method)

     fun getNames(fileName:String):List<Names> {
        val data = FileUtils.getJsonDataFromAsset(fileName)
        val type: Type? = object : TypeToken<List<Names>>() {}.type
        val gson = Gson()
        return gson.fromJson(data, type)
    }

    fun getDuas(fileName:String):List<Dua> {
        val data = FileUtils.getJsonDataFromAsset(fileName)
        val type: Type? = object : TypeToken<List<Dua>>() {}.type
        val gson = Gson()
        // override fun getResponseType(): Class<EditOrder> = EditOrder::class.java
        return gson.fromJson(data, type)
    }
}
