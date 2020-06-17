package com.nehal.seher.room

class DuaDatabseHelper(private val duaCollectionDao: DuaCollectionDao) {
    suspend fun getAllDuas() = duaCollectionDao.getAllDua()
}