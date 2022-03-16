package com.gustavozreis.dogvacionalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
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

        viewModel.dogObject.observe(this) { newDogObject ->
            tvDogPhrase?.text = newDogObject?.imgUrl
        }

        btnNewDog?.setOnClickListener {
            getNewDogFromAPI()
        }

    }


    /*
    This functions retrieves e new dog object from the API and manipulates the views with it
     */
    fun getNewDogFromAPI() {
        viewModel.getNewDogObject()
        // dog phrase placeholder
        //tvDogPhrase?.text = dogObject?.imgUrl
        // dog image placeholder
        //ivDogImage?.setImageResource(R.drawable.dog_example)
    }


}