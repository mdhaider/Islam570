package com.nehal.seher.model

import com.google.gson.annotations.SerializedName

data class Names(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("transliteration") val transliteration: String,
    @SerializedName("meaning") val meaning: String,
    @SerializedName("benefits") val benefits: String
)