package com.example.sampleapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.data.NewsRepository
import com.example.sampleapp.network.Article
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun searchNews(query: String, page: Int = 1) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.searchNews(query, page)
                _articles.value = response.articles
                _error.value = false
            } catch (e: Exception) {
                _error.value = true
            }
            _isLoading.value = false
        }
    }
}