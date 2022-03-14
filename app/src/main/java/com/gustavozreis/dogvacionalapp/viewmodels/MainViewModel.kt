package com.gustavozreis.dogvacionalapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavozreis.dogvacionalapp.network.DogApi
import com.gustavozreis.dogvacionalapp.network.DogApiService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    /*
    This function retrieve a new dog photo from the API
     */
    fun getNewDogPhoto() {
        viewModelScope.launch {
            DogApi.retrofitService.getDogPhoto()
        }

    }

}