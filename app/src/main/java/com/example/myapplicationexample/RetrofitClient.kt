package com.example.myapplicationexample

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Use 10.0.2.2 if you're using Android Emulator
    // Use your IP (e.g., 192.168.x.x) if on real Android device
    private const val BASE_URL = "http://10.82.192.211/fskmapi/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
