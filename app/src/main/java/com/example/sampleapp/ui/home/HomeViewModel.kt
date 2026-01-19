package com.example.sampleapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.data.LocalStorageManager
import com.example.sampleapp.data.NewsRepository
import com.example.sampleapp.network.Article
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: NewsRepository,
    private val localStorage: LocalStorageManager
) : ViewModel() {

    // Top Headlines
    private val _topHeadlines = MutableLiveData<List<Article>>()
    val topHeadlines: LiveData<List<Article>> = _topHeadlines

    private val _isLoadingHeadlines = MutableLiveData<Boolean>()
    val isLoadingHeadlines: LiveData<Boolean> = _isLoadingHeadlines

    // For You (Preferences)
    private val _forYouArticles = MutableLiveData<List<Article>>()
    val forYouArticles: LiveData<List<Article>> = _forYouArticles

    private val _isLoadingForYou = MutableLiveData<Boolean>()
    val isLoadingForYou: LiveData<Boolean> = _isLoadingForYou

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun loadAllNews() {
        getTopHeadlines()
        getForYouNews()
    }

    fun getTopHeadlines() {
        viewModelScope.launch {
            _isLoadingHeadlines.value = true
            try {
                Log.d("HomeViewModel", "Fetching headlines...")
                val response = repository.getTopHeadlines()
                Log.d("HomeViewModel", "Got ${response.articles.size} headlines")
                _topHeadlines.value = response.articles
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching headlines: ${e.message}", e)
                _topHeadlines.value = emptyList()
            }
            _isLoadingHeadlines.value = false
        }
    }

    fun getForYouNews() {
        viewModelScope.launch {
            _isLoadingForYou.value = true
            try {
                // Get user preferences
                val preferences = localStorage.getUserPreferences()
                // Use displayName directly (Technology, Business, World, Economy)
                val categories = preferences.map { it.displayName }
                
                Log.d("HomeViewModel", "Fetching for you with categories: $categories")
                
                if (categories.isNotEmpty()) {
                    val response = repository.getPreferenceNews(categories)
                    Log.d("HomeViewModel", "Got ${response.articles.size} for you articles")
                    _forYouArticles.value = response.articles
                } else {
                    _forYouArticles.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching for you: ${e.message}", e)
                _forYouArticles.value = emptyList()
            }
            _isLoadingForYou.value = false
        }
    }
}