package com.gustavozreis.dogvacionalapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavozreis.dogvacionalapp.network.DogApi
import com.gustavozreis.dogvacionalapp.network.DogPhotoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    var _dogObject = MutableLiveData<DogPhotoModel?>() // changeable livedata
    val dogObject: LiveData<DogPhotoModel?> // non-changeable livedata
        get() = _dogObject

    // Variable for coroutine job
    private var viewModelJob = Job()

    // CoroutineScope with the main dispatcher
    // (it's ok to use the main thread because retrofit will automatically use a background thread)
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /*
    This function retrieves a new dog photo from the API, uses the created viewmodel coroutine (viewModelJob)
     */
    fun getNewDogObject() {
           coroutineScope.launch {
               val getDogPhotoModelDeferred = DogApi.retrofitService.getDogObject()
               try {
                   val newDogObject = getDogPhotoModelDeferred.await()
                   _dogObject.value = newDogObject
               } catch (t: Throwable) {
                   _dogObject.value = null
               }
           }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}