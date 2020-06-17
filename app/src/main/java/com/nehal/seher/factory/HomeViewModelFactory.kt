package com.nehal.seher.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nehal.seher.repository.HomeRepository
import com.nehal.seher.repository.MainRepository
import com.nehal.seher.retrofit.ApiHelper
import com.nehal.seher.repository.QuraanRepository
import com.nehal.seher.viewmodels.HomeViewModel
import com.nehal.seher.viewmodels.MainViewModel
import com.nehal.seher.viewmodels.QuraanViewModel

class HomeViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(HomeRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

