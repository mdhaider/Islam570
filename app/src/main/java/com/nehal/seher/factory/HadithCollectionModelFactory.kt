package com.nehal.seher.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nehal.seher.room.HadithCollectionRepository
import com.nehal.seher.room.HadithDatabseHelper
import com.nehal.seher.viewmodels.HadithListViewModel

class HadithCollectionModelFactory(private val hadithDatabseHelper: HadithDatabseHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HadithListViewModel::class.java)) {
            return HadithListViewModel(HadithCollectionRepository(hadithDatabseHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

