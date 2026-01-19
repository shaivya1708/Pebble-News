package com.example.sampleapp.network

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)