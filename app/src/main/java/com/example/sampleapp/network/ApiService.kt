package com.example.sampleapp.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("language") language: String = "en",
        @Query("pageSize") pageSize: Int = 50,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String
    ): NewsResponse

    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("from") from: String? = null,
        @Query("language") language: String = "en",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("pageSize") pageSize: Int = 50,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}