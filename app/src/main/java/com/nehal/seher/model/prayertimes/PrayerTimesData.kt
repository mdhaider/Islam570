package com.nehal.seher.model.prayertimes

import com.google.gson.annotations.SerializedName

data class PrayerTimesData(
    @SerializedName("timings") val timings: Timings,
    @SerializedName("date") val date: Date,
    @SerializedName("meta") val prayerTimesMeta: PrayerTimesMeta
)