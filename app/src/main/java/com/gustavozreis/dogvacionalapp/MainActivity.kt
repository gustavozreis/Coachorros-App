package com.gustavozreis.dogvacionalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.gustavozreis.dogvacionalapp.databinding.ActivityMainBinding
import com.gustavozreis.dogvacionalapp.network.DogApiService
import com.gustavozreis.dogvacionalapp.network.DogPhotoModel
import com.gustavozreis.dogvacionalapp.viewmodels.MainViewModel
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()
    var binding: ActivityMainBinding? = null
    var dogObject: DogPhotoModel? = null

    var ivDogImage: ImageView? = null
    var tvDogPhrase: TextView? = null
    var btnNewDog: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)



        ivDogImage = binding?.ivDogImage
        tvDogPhrase = binding?.tvMessage
        btnNewDog = binding?.btnNewdog

        var dogObject: DogPhotoModel?
        viewModel.dogObject.observe(this) { dogObjectViewModel ->
            dogObject = dogObjectViewModel
        }

    }


    /*
    This functions retrieves e new dog object from the API and manipulates the views with it
     */
    fun getNewDogFromAPI() {
        viewModel.getNewDogObject()
        // dog phrase placeholder
        tvDogPhrase?.text = dogObject?.imgUrl
        // dog image placeholder
        //ivDogImage?.setImageResource(R.drawable.dog_example)
    }


}