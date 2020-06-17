package com.nehal.seher.model

import com.google.gson.annotations.SerializedName

data class Hadis(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("subTitle") val subTitle: String
)