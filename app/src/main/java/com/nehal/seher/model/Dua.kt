package com.nehal.seher.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "duas")
@Parcelize
data class Dua(

    @SerializedName("id")
    @PrimaryKey val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,
    @SerializedName("duaAr")
    val duaAr: String? = null,
    @SerializedName("transLiteration")
    val transLiteration: String? = null,
    @SerializedName("meaning")
    val meaning: String? = null,
    @SerializedName("source")
    val source: String? = null
) : Parcelable