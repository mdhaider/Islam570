package com.nehal.seher.model.hadith

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Collection")
data class HadithCollection(
    @PrimaryKey
    @ColumnInfo(name = "CollectionID")
    val collectionId: Int? = null,

    @ColumnInfo(name = "name_en")
    val nameEn: String? = null,

    @ColumnInfo(name = "desc_en")
    val descEn: String? = null,

    @Embedded val book: HadithBook?=null
)