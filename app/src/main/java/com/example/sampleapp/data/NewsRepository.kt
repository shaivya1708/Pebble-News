package com.example.sampleapp.data

import com.example.sampleapp.config.Config
import com.example.sampleapp.network.ApiService
import com.example.sampleapp.network.RetrofitClient
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NewsRepository(private val apiService: ApiService) {

    // Get date from 7 days ago in format yyyy-MM-dd
    private fun getFromDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return dateFormat.format(calendar.time)
    }

    suspend fun getTopHeadlines(page: Int = 1) = 
        apiService.getTopHeadlines(page = page, apiKey = Config.API_KEY)

    suspend fun searchNews(query: String, page: Int = 1) =
        apiService.getEverything(
            query = query,
            from = getFromDate(),
            apiKey = Config.API_KEY
        )

    suspend fun getPreferenceNews(categories: List<String>, page: Int = 1): com.example.sampleapp.network.NewsResponse {
        // Build query like "technology OR business OR world"
        val query = categories.joinToString(" OR ")
        
        return apiService.getEverything(
            query = query,
            from = getFromDate(),
            apiKey = Config.API_KEY
        )
    }
}