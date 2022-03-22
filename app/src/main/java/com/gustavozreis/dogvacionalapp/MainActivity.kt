package com.gustavozreis.dogvacionalapp

import android.app.Dialog
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.setPadding
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

    var loadingProgressBar: Dialog? = null

    var loadingRotatingLogo: AnimationDrawable? = null

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
        viewModel.imagePhrase.observe(this) { newPhrase ->
            imagePhrase = newPhrase
        }

        // Livedata observer that changes the dog image
        viewModel.dogObject.observe(this) { newDogObject ->
            Glide.with(this)
                .load(newDogObject?.imgUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        // Show error if the image loading fails
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
                        // Change the image textview once the image is loaded
                        tvDogPhrase?.text = imagePhrase
                        cancelProgressBarDialog()
                        return false
                    }

                })
                .into(ivDogImage!!)
        }

        getNewDogFromAPI()

        btnNewDog?.setOnClickListener {
            showRotatingLogoLoading()
            getNewDogFromAPI()
            tvDogPhrase?.text = ""
        }

    }


    /*
    This function invokes the update coachorro of the viewModel
     */
    private fun getNewDogFromAPI() {
        viewModel.getNewCoachorro()
    }

    /*
    This function will show the progress bar dialog      [NOT USING ANYMORE WILL CLEAN UP WHEN PROJECT IS DONE]
     */
    private fun showProgressBarDialog() {
        loadingProgressBar = Dialog(this)
        loadingProgressBar?.setCancelable(false)
        loadingProgressBar?.setContentView(R.layout.image_loading_progress_bar)
        loadingProgressBar?.show()
    }

    /*
    This function will cancel the progress bar dialog [NOT USING ANYMORE WILL CLEAN UP WHEN PROJECT IS DONE]
     */
    private fun cancelProgressBarDialog() {
        if (loadingProgressBar != null) {
            loadingProgressBar?.dismiss()
            loadingProgressBar = null
        }

    }

    /*
    This function will show the rotating logo on the imageview when retrieving the image
     */
    private fun showRotatingLogoLoading() {
        ivDogImage?.apply {
            setBackgroundResource(R.drawable.loading_animation)
            loadingRotatingLogo = background as AnimationDrawable
        }
        loadingRotatingLogo?.start()
    }
}


