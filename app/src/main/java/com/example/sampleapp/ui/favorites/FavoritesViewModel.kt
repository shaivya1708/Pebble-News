package com.example.sampleapp.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapp.data.LocalRepository
import com.example.sampleapp.network.Article

class FavoritesViewModel(private val repository: LocalRepository) : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    fun getFavorites() {
        _articles.value = repository.getFavorites()
    }
}