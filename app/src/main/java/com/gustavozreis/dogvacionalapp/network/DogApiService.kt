package com.gustavozreis.dogvacionalapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://dog.ceo/api/"

// moshi object (json converter)
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// retrofit object (http callbacks)
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface DogApiService {
    // this function returns the json object with the image url and the success/error message
    @GET("breeds/image/random")
    fun getDogObject(): Call<String>

}

// create a singleton of the retrofit api access
object DogApi {
    val retrofitService : DogApiService by lazy {
        retrofit.create(DogApiService::class.java)
    }
}