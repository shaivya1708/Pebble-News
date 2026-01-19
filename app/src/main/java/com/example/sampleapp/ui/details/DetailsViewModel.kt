package com.example.sampleapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapp.data.LocalRepository
import com.example.sampleapp.network.Article

class DetailsViewModel(private val repository: LocalRepository) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _isReadLater = MutableLiveData<Boolean>()
    val isReadLater: LiveData<Boolean> = _isReadLater

    fun checkArticleStatus(articleUrl: String) {
        _isFavorite.value = repository.isFavorite(articleUrl)
        _isReadLater.value = repository.isReadLater(articleUrl)
    }

    fun toggleFavorite(article: Article) {
        _isFavorite.value = repository.toggleFavorite(article)
    }

    fun toggleReadLater(article: Article) {
        _isReadLater.value = repository.toggleReadLater(article)
    }

    // Keep old methods for backward compatibility
    fun addToFavorites(article: Article) {
        repository.addFavorite(article)
        _isFavorite.value = true
    }

    fun addToReadLater(article: Article) {
        repository.addReadLater(article)
        _isReadLater.value = true
    }
}