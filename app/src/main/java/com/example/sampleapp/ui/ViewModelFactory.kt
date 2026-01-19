package com.example.sampleapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sampleapp.data.AuthRepository
import com.example.sampleapp.data.LocalRepository
import com.example.sampleapp.data.LocalStorageManager
import com.example.sampleapp.data.NewsRepository
import com.example.sampleapp.network.RetrofitClient
import com.example.sampleapp.ui.auth.AuthViewModel
import com.example.sampleapp.ui.details.DetailsViewModel
import com.example.sampleapp.ui.favorites.FavoritesViewModel
import com.example.sampleapp.ui.home.HomeViewModel
import com.example.sampleapp.ui.readlater.ReadLaterViewModel
import com.example.sampleapp.ui.search.SearchViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private val localStorage by lazy { LocalStorageManager.getInstance(context) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(NewsRepository(RetrofitClient.instance), localStorage) as T
        }
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(LocalRepository(localStorage)) as T
        }
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(NewsRepository(RetrofitClient.instance)) as T
        }
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(AuthRepository(localStorage)) as T
        }
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(LocalRepository(localStorage)) as T
        }
        if (modelClass.isAssignableFrom(ReadLaterViewModel::class.java)) {
            return ReadLaterViewModel(LocalRepository(localStorage)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}