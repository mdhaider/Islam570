package com.nehal.seher.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nehal.seher.repository.MainRepository
import com.nehal.seher.retrofit.ApiHelper
import com.nehal.seher.repository.QuraanRepository
import com.nehal.seher.viewmodels.MainViewModel
import com.nehal.seher.viewmodels.QuraanViewModel

class QuraanViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuraanViewModel::class.java)) {
            return QuraanViewModel(QuraanRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

