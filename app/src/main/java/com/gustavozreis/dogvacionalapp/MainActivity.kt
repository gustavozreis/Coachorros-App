package com.gustavozreis.dogvacionalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
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

    }


    /*
    This functions retrieves e new dog object from the API and manipulates the views with it
     */
    fun getNewDogFromAPI() {
        val dogObjectCall: Call<DogPhotoModel> = viewModel.getNewDogObject()
        // dog phrase placeholder
        tvDogPhrase?.text = "Dê mais atenção ao que você tem de bom na sua vida!"
        // dog image placeholder
        ivDogImage?.setImageResource(R.drawable.dog_example)
    }


}