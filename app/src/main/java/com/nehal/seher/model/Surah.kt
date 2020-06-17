package com.nehal.seher.model

import com.google.gson.annotations.SerializedName

data class Surah(
    @SerializedName("id") val id: Int,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("nameAr") val nameAr: String,
    @SerializedName("nameEnMeaning") val nameEnMeaning: String,
    @SerializedName("textUrl") val textUrl: String,
    @SerializedName("audioUrl") val audioUrl: String,
    @SerializedName("totalAyah") val totalAyah: Int,
    @SerializedName("totalRuku") val totalRuku: Int
)