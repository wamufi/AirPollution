package com.wamufi.airpollution.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wamufi.airpollution.repository.AirKoreaRepository

class AirKoreaViewModelFactory(private val repository: AirKoreaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AirKoreaRepository::class.java).newInstance(repository)
    }
}