package com.example.appli_mobile_clemence_pierre.avatar_api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageWebService {
    @GET("/5.x/avataaars/png")
    suspend fun get_image(@Query("seed") name: String): ResponseBody
}
