package com.nehal.seher.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nehal.seher.model.DuaModel
import com.nehal.seher.model.hadith.HadithBook
import com.nehal.seher.model.hadith.HadithCollection

@Database(entities = [DuaModel::class], version = 1,exportSchema = false)
abstract class DuaAndAdhkarDatabase : RoomDatabase() {

    abstract fun duaDao(): DuaCollectionDao

    companion object {
        private var DB_CLIENT = "dua.db"
        private var instance: DuaAndAdhkarDatabase? = null

        fun getInstance(context: Context): DuaAndAdhkarDatabase? {
            if (instance == null) {
                synchronized(DuaAndAdhkarDatabase::class) {
                    instance = Room.databaseBuilder(context, DuaAndAdhkarDatabase::class.java, DB_CLIENT)
                        .createFromAsset(DB_CLIENT)
                        .build()
                }
            }
            return instance
        }
    }
}