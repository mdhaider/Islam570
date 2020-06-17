package com.nehal.seher.repository

import com.nehal.seher.room.dbhelper.DuaDatabseHelper

class DuaRepository(private val duaDatabseHelper: DuaDatabseHelper) {
    suspend fun getAllDuas() = duaDatabseHelper.getAllDuas()
}