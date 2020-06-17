package com.nehal.seher.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Dua")
@Parcelize
data class DuaModel(
    val category: String?=null,
    val favourite: String?=null,
    val audio_name: String?=null,
    val dua_transliteration: String?=null,
    val english_translation: String?=null,

    @PrimaryKey
    val dua_id: Int?=null,
    val dua_title: String?=null,
    val dua_arabic: String?=null,
    val additional_info: String?=null,
    val counter: Int=0,
    val reference: String?=null,
    val urdu_translation: String?=null,
    val id: Int=0
) : Parcelable