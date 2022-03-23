package com.gustavozreis.dogvacionalapp

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.core.view.setPadding
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()
    var binding: ActivityMainBinding? = null
    var dogObject: DogPhotoModel? = null

    var ivDogImage: ImageView? = null
    var tvDogPhrase: TextView? = null
    var cvImageContainer: CardView? = null

    var btnNewDog: Button? = null

    var loadingRotatingLogo: AnimationDrawable? = null

    var btnShareButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        ivDogImage = binding?.ivDogImage
        tvDogPhrase = binding?.tvMessage
        btnNewDog = binding?.btnNewdog
        btnShareButton = binding?.btnShare
        cvImageContainer = binding?.cvMain

        // These 2 functions will start the loading animation
        // and retrieve the first imagem and phrase
        showRotatingLogoLoading()
        getNewDogFromAPI()

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
                        Toast.makeText(
                            this@MainActivity,
                            "Erro ao carregar o Coachorro, verifique se sua conexão está funcionando e tente novamente.",
                            Toast.LENGTH_LONG
                        ).show()
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

        btnShareButton?.setOnClickListener {
            var currentImageBitmap: Bitmap = createBitmapFromView(ivDogImage)
            var currentImageUri: Uri? = null
            lifecycleScope.launch {
                currentImageUri = saveImage(currentImageBitmap)
                shareImageUri(currentImageUri)
            }

        }

    }


    /*
    This function invokes the update coachorro of the viewModel
     */
    private fun getNewDogFromAPI() {
        viewModel.getNewCoachorro()
    }

    /*
    This function shows the rotating logo on the imageview when retrieving the image
     */
    private fun showRotatingLogoLoading() {
        ivDogImage?.apply {
            setBackgroundResource(R.drawable.loading_animation)
            loadingRotatingLogo = background as AnimationDrawable
        }
        loadingRotatingLogo?.start()
    }

    /*
    This function creates a bitmap out of a view
     */
    private fun createBitmapFromView(view: ImageView?): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view!!.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        view.draw(canvas)
        return returnedBitmap
    }

    /*
  This function saves the image on the cacheDir and returns it URI
   */
    private suspend fun saveImage(image: Bitmap): Uri? {
        var uri: Uri? = null
        withContext(Dispatchers.IO) {
            try {
                val imagesFolder = File(cacheDir, "images")
                imagesFolder.mkdirs() // creates new dir in cachedir

                val file = File( // creates file object with path of created dir
                    imagesFolder, "shared_image.png"
                )

                val fileOutputStream = FileOutputStream(file)

                image.compress( // compress bitmap
                    Bitmap.CompressFormat.PNG,
                    90,
                    fileOutputStream
                )

                fileOutputStream.flush() // send bitmap data to created file
                fileOutputStream.close() // close stream

                ///data/data/com.gustavozreis.dogvacionalapp/cache/images/shared_image.png
                uri = FileProvider.getUriForFile( // retrieves the file uri
                    this@MainActivity,
                    "com.gustavozreis.fileprovider",
                    file
                )

            } catch (e: IOException) {
                Toast.makeText(this@MainActivity, "Error while saving the file", Toast.LENGTH_LONG).show()
                Log.d(TAG, "IOException while saving the file")
            }
        }
        return uri
    }

    /*
    This function shares the image
     */
    private fun shareImageUri(uri: Uri?) {
        if (uri == null) {
            Toast.makeText(this, "URI É NULO", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                this.putExtra(Intent.EXTRA_STREAM, uri)
                this.type = "image/png"
            }
            startActivity(intent)
        }
    }


}


