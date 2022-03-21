package com.gustavozreis.dogvacionalapp

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
        //tvDogPhrase?.text = Phrases.listOfPhrases.random()

        // Placeholder variable for the image phrase
        var imagePhrase: String = ""

        // Livedata observer that changes the image phrase
        viewModel.imagePhrase.observe(this@MainActivity) { newPhrase ->
            imagePhrase = newPhrase
        }

        // Livedata observer that changes the dog image
        viewModel.dogObject.observe(this) { newDogObject ->
            Glide.with(this)
                .load(newDogObject?.imgUrl)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                       Log.e("TAG", "Image loading error!")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        tvDogPhrase?.text = imagePhrase
                        return false
                    }

                })
                .into(ivDogImage!!)
        }



        btnNewDog?.setOnClickListener {
            getNewDogFromAPI()
        }

    }


    /*
    This function invokes the update coachorro of the viewModel
     */
    private fun getNewDogFromAPI() {
        viewModel.getNewCoachorro()
    }


}