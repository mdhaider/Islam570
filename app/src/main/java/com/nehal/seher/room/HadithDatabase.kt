package com.nehal.seher.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nehal.seher.model.hadith.HadithBook
import com.nehal.seher.model.hadith.HadithCollection

@Database(entities = [HadithCollection::class], version = 3,exportSchema = false)
abstract class HadithDatabase : RoomDatabase() {

    abstract fun clientDao(): HadithCollectionDao

    companion object {
        private var DB_CLIENT = "hadith.db"
        private var instance: HadithDatabase? = null

        fun getInstance(context: Context): HadithDatabase? {
            if (instance == null) {
                synchronized(HadithDatabase::class) {
                    instance = Room.databaseBuilder(context, HadithDatabase::class.java, DB_CLIENT)
                        .createFromAsset(DB_CLIENT)
                        .build()
                }
            }
            return instance
        }
    }
}