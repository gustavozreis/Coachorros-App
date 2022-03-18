package com.gustavozreis.dogvacionalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.gustavozreis.dogvacionalapp.data.Phrases
import com.gustavozreis.dogvacionalapp.data.Phrases.listOfPhrases
import com.gustavozreis.dogvacionalapp.databinding.ActivityMainBinding
import com.gustavozreis.dogvacionalapp.network.DogPhotoModel
import com.gustavozreis.dogvacionalapp.viewmodels.MainViewModel
import java.util.*

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

        // Gets a random phrase for the image
        tvDogPhrase?.text = Phrases.listOfPhrases.random()

        // Livedata observer that changes the dog image
        viewModel.dogObject.observe(this) { newDogObject ->
            Glide.with(this).load(newDogObject?.imgUrl).into(ivDogImage!!)
        }

        btnNewDog?.setOnClickListener {
            getNewDogFromAPI()
        }

    }


    /*
    This functions retrieves e new dog object from the API and manipulates the views with it
     */
    private fun getNewDogFromAPI() {
        viewModel.getNewDogObject()
        tvDogPhrase?.text = Phrases.listOfPhrases.random()
    }


}