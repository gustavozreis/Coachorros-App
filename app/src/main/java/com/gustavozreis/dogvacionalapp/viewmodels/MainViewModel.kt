package com.gustavozreis.dogvacionalapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavozreis.dogvacionalapp.network.DogApi
import com.gustavozreis.dogvacionalapp.network.DogPhotoModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    var _dogObject = MutableLiveData<String?>()
    val dogObject: LiveData<String?>
        get() = _dogObject

    /*
    This function retrieve a new dog photo from the API, uses the viewmodel coroutine
     */
    fun getNewDogObject() {
        viewModelScope.launch {
            DogApi.retrofitService.getDogObject().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _dogObject.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    _dogObject.value = "Error: " + t.message
                }
            })
        }
    }
}