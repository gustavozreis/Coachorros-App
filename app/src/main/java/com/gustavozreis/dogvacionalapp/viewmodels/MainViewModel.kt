package com.gustavozreis.dogvacionalapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gustavozreis.dogvacionalapp.data.Phrases
import com.gustavozreis.dogvacionalapp.network.DogApi
import com.gustavozreis.dogvacionalapp.network.DogPhotoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var _dogObject = MutableLiveData<DogPhotoModel?>() // changeable livedata
    val dogObject: LiveData<DogPhotoModel?> // non-changeable livedata
        get() = _dogObject

    var _imagePhrase = MutableLiveData<String>() // changeable image phrase live data
    val imagePhrase: LiveData<String> // non-changeable image phrase live data
        get() = _imagePhrase

    // Variable for coroutine job
    private var viewModelJob = Job()

    // CoroutineScope with the main dispatcher
    // (it's ok to use the main thread because retrofit will automatically use a background thread)
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /*
    This function retrieves a new dog photo from the API, and a new phrase from
     the data using the created viewmodel coroutine (viewModelJob)
     */
    fun getNewCoachorro() {
           coroutineScope.launch {
               val getDogPhotoModelDeferred = DogApi.retrofitService.getDogObject()
               try {
                   val newDogObject = getDogPhotoModelDeferred.await()
                   _dogObject.value = newDogObject

               } catch (t: Throwable) {
                   _dogObject.value = null
               }
               _imagePhrase.value = Phrases.listOfPhrases.random()
           }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}