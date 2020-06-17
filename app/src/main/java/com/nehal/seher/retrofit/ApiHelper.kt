package com.nehal.seher.retrofit

class ApiHelper(private val apiService: ApiService) {
    suspend fun getSurahs() = apiService.getSurahs()
    suspend fun getParas() = apiService.getParas()
    suspend fun getHadis() = apiService.getHadis()
    suspend fun getUrduPosters() = apiService.gutUrduPosters()
    suspend fun getHijriFromGreg(date:String, adjustment:Int?) = apiService.getHijriFromGreg(date,adjustment)
    suspend fun getPrayerTimes(
        date_or_timestamp:String,
        latitude: String,
        longitude: String,
        method: String
    ) = apiService.getPrayerTimes(date_or_timestamp,latitude,longitude, method)

}