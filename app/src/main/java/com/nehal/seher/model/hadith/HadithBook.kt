package com.nehal.seher.model.hadith

import androidx.room.ColumnInfo
import androidx.room.Entity


data class HadithBook(

    @ColumnInfo(name = "BookID")
    val bookId: String,

    @ColumnInfo(name = "nameshort_en")
    val nameShortEn: String? = null
)