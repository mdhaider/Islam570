package com.nehal.seher.room

import androidx.room.Dao
import androidx.room.Query
import com.nehal.seher.model.hadith.HadithBook
import com.nehal.seher.model.hadith.HadithCollection


@Dao
interface HadithCollectionDao {

    @Query("SELECT * FROM Collection")
    suspend fun getAllCollection(): List<HadithCollection>

    @Query("SELECT * FROM Collection WHERE collectionid=1")
    suspend fun getAllBook(): List<HadithBook>
}