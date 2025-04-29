package com.example.geoquest.ui.viewModels.factories

import androidx.lifecycle.ViewModelProvider
import com.example.geoquest.ui.viewModels.CreateRewardViewModel

class CreateRewardViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateRewardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateRewardViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
