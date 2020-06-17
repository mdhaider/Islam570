package com.nehal.seher.room.dbhelper

import com.nehal.seher.room.dao.DuaCollectionDao

class DuaDatabseHelper(private val duaCollectionDao: DuaCollectionDao) {
    suspend fun getAllDuas() = duaCollectionDao.getAllDua()
}