package com.gustavozreis.dogvacionalapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavozreis.dogvacionalapp.network.DogApi
import com.gustavozreis.dogvacionalapp.network.DogApiService
import com.gustavozreis.dogvacionalapp.network.DogPhotoModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    var _dogImageUrl = MutableLiveData<DogPhotoModel?>()
    val dogImageUrl: LiveData<DogPhotoModel?>
        get() = _dogImageUrl



    /*
    This function retrieve a new dog photo from the API, uses the viewmodel coroutine
     */
    suspend fun getNewDogObject() {
        DogApi.retrofitService.getDogObject().enqueue(object : Callback<DogPhotoModel>{
            override fun onResponse(call: Call<DogPhotoModel>, response: Response<DogPhotoModel>) {
                _dogImageUrl.value = response.body()
            }

            override fun onFailure(call: Call<DogPhotoModel>, t: Throwable) {
                _dogImageUrl.value = null
            }
        })
    }

}