package com.example.sampleapp.ui.readlater

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapp.data.LocalRepository
import com.example.sampleapp.network.Article

class ReadLaterViewModel(private val repository: LocalRepository) : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    fun getReadLaterArticles() {
        _articles.value = repository.getReadLater()
    }
}