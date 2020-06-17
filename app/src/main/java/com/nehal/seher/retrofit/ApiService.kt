package com.nehal.seher.retrofit

import com.nehal.seher.model.*
import com.nehal.seher.model.gregtohijri.HijriDateResponse
import com.nehal.seher.model.prayertimes.PrayerTimesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("all-surah.json?raw=true")
    suspend fun getSurahs(): List<Surah>

    @GET("all-paras.json?raw=true")
    suspend fun getParas(): List<Para>

    @GET("hadith.json?raw=true")
    suspend fun getHadis(): List<Hadis>

    @GET("posters.json?raw=true")
    suspend fun gutUrduPosters(): List<Poster>

    @GET("gToH")
    suspend fun getHijriFromGreg(@Query("date") date:String,
                                 @Query("adjustment") adjustment:Int?): HijriDateResponse

    @GET("timings")
    suspend fun getPrayerTimes(
        @Query("date_or_timestamp") dateOrTimeSTamp:String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("method") method: String
    ): PrayerTimesResponse
}