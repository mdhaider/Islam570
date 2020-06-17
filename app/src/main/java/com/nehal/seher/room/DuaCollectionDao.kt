package com.nehal.seher.room

import androidx.room.Dao
import androidx.room.Query
import com.nehal.seher.model.DuaModel
import com.nehal.seher.model.hadith.HadithBook
import com.nehal.seher.model.hadith.HadithCollection


@Dao
interface DuaCollectionDao {

    @Query("SELECT * FROM Dua")
    suspend fun getAllDua(): List<DuaModel>

}