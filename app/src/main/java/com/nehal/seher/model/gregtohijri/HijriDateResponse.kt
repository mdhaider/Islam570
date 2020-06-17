package com.nehal.seher.model.gregtohijri

import com.google.gson.annotations.SerializedName

data class HijriDateResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("data") val hijriDateResponseData: HijriDateResponseData
)