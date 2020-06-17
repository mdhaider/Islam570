package com.nehal.seher.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Poster(
    @SerializedName("id") val id: Int?=null,
    @SerializedName("category") val category: String?=null,
    @SerializedName("title") val title: String?=null,
    @SerializedName("uploadDate") val uploadDate: String?=null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("imgUrl") val imgUrl: String? = null
) : Parcelable