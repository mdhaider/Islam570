package com.nehal.seher.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nehal.seher.room.dbhelper.DuaDatabseHelper
import com.nehal.seher.repository.DuaRepository
import com.nehal.seher.viewmodels.DuaViewModel

class DuaModelFactory(private val duaDatabseHelper: DuaDatabseHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DuaViewModel::class.java)) {
            return DuaViewModel(
                DuaRepository(
                    duaDatabseHelper
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

