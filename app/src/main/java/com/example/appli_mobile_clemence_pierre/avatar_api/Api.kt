package com.example.appli_mobile_clemence_pierre.avatar_api

import okhttp3.OkHttpClient
import retrofit2.Retrofit

object Api {
    private val retrofit by lazy {
        // client HTTP
        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.dicebear.com/")
            .client(okHttpClient)
//            .addConverterFactory(jsonSerializer.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val imageWebService: ImageWebService by lazy {
        retrofit.create(ImageWebService::class.java)
    }
}
