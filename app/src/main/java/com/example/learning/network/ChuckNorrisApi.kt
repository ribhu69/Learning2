package com.example.learning.network

import retrofit2.http.GET

interface ChuckNorrisApi {
    @GET("jokes/random")
    suspend fun getRandomJoke(): JokeResponse
}
