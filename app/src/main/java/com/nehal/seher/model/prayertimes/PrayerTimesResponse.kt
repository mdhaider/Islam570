package com.nehal.seher.model.prayertimes

import com.google.gson.annotations.SerializedName

data class PrayerTimesResponse(

    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("data") val prayerTimesData: PrayerTimesData
)