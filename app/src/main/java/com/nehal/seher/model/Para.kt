package com.nehal.seher.model

import com.google.gson.annotations.SerializedName

data class Para(
    @SerializedName("id") val id: Int,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("nameAr") val nameAr: String,
    @SerializedName("textUrl") val textUrl: String,
    @SerializedName("audioUrl") val audioUrl: String
)