package com.udacity.asteroidradar.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidService {
    @GET("neo/rest/v1/feed?")
    suspend fun getData(@Query("start_date") start_date: String, @Query("end_date") end_date: String, @Query("api_key") api_key: String): Response<String>

    @GET("planetary/apod?")
    suspend fun getPhoto(@Query ("api_key") api_key: String):Response<String>
}