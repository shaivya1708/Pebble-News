package com.example.sampleapp.data

import com.example.sampleapp.network.Article

/**
 * Local repository for favorites and read later (replaces Firestore)
 */
class LocalRepository(private val localStorage: LocalStorageManager) {

    fun addFavorite(article: Article) {
        localStorage.addFavorite(article)
    }

    fun removeFavorite(article: Article) {
        localStorage.removeFavorite(article)
    }

    fun isFavorite(url: String): Boolean {
        return localStorage.isFavorite(url)
    }

    fun toggleFavorite(article: Article): Boolean {
        return localStorage.toggleFavorite(article)
    }

    fun getFavorites(): List<Article> {
        return localStorage.getFavorites()
    }

    fun addReadLater(article: Article) {
        localStorage.addReadLater(article)
    }

    fun removeReadLater(article: Article) {
        localStorage.removeReadLater(article)
    }

    fun isReadLater(url: String): Boolean {
        return localStorage.isReadLater(url)
    }

    fun toggleReadLater(article: Article): Boolean {
        return localStorage.toggleReadLater(article)
    }

    fun getReadLater(): List<Article> {
        return localStorage.getReadLater()
    }
}