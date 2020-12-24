package com.artelsv.ruu.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.artelsv.ruu.RetrofitTools.RetrofitHelper

class SendDataViewModelFactory(private val retrofitHelper: RetrofitHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SendDataViewModel::class.java)) {
            return SendDataViewModel(Repositoty(retrofitHelper)) as T
        }
        throw IllegalArgumentException() // Выпадет если класса не будет
    }

}