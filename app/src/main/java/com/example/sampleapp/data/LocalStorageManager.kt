package com.example.sampleapp.data

import android.content.Context
import android.content.SharedPreferences
import com.example.sampleapp.network.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Local storage manager for user data, favorites, and read later articles
 * Favorites and read later lists are stored per-user
 */
class LocalStorageManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val PREFS_NAME = "sample_app_prefs"
        private const val KEY_CURRENT_USER = "current_user_email"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USERS = "registered_users"
        private const val KEY_FAVORITES_PREFIX = "favorites_"
        private const val KEY_READ_LATER_PREFIX = "read_later_"
        private const val KEY_PREFERENCES_PREFIX = "preferences_"

        @Volatile
        private var INSTANCE: LocalStorageManager? = null

        fun getInstance(context: Context): LocalStorageManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalStorageManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    // Helper to get user-specific key
    private fun getUserKey(prefix: String): String {
        val email = getCurrentUserEmail() ?: "guest"
        // Sanitize email for use as key (replace special chars)
        val sanitizedEmail = email.replace("@", "_at_").replace(".", "_dot_")
        return "$prefix$sanitizedEmail"
    }

    // ==================== USER AUTHENTICATION ====================

    // Data class for storing user credentials
    private data class UserCredentials(val email: String, val password: String)

    private fun getRegisteredUsers(): MutableMap<String, String> {
        val json = prefs.getString(KEY_USERS, null) ?: return mutableMapOf()
        val type = object : TypeToken<MutableMap<String, String>>() {}.type
        return try {
            gson.fromJson(json, type)
        } catch (e: Exception) {
            mutableMapOf()
        }
    }

    private fun saveRegisteredUsers(users: Map<String, String>) {
        val json = gson.toJson(users)
        prefs.edit().putString(KEY_USERS, json).apply()
    }

    fun registerUser(email: String, password: String): Boolean {
        val users = getRegisteredUsers()
        
        // Check if user already exists
        if (users.containsKey(email)) {
            return false // User already exists
        }
        
        // Add new user
        users[email] = password
        saveRegisteredUsers(users)
        
        // Log in the new user
        prefs.edit()
            .putString(KEY_CURRENT_USER, email)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
        return true
    }

    fun loginUser(email: String, password: String): Boolean {
        val users = getRegisteredUsers()
        val savedPassword = users[email]
        
        return if (password == savedPassword) {
            prefs.edit()
                .putString(KEY_CURRENT_USER, email)
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .apply()
            true
        } else {
            false
        }
    }

    fun logoutUser() {
        prefs.edit()
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .remove(KEY_CURRENT_USER)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getCurrentUserEmail(): String? {
        return if (isLoggedIn()) prefs.getString(KEY_CURRENT_USER, null) else null
    }

    // ==================== FAVORITES (Per-User) ====================

    fun addFavorite(article: Article) {
        val favorites = getFavorites().toMutableList()
        if (favorites.none { it.url == article.url }) {
            favorites.add(article)
            saveFavorites(favorites)
        }
    }

    fun removeFavorite(article: Article) {
        val favorites = getFavorites().toMutableList()
        favorites.removeAll { it.url == article.url }
        saveFavorites(favorites)
    }

    fun isFavorite(url: String): Boolean {
        return getFavorites().any { it.url == url }
    }

    fun toggleFavorite(article: Article): Boolean {
        return if (isFavorite(article.url)) {
            removeFavorite(article)
            false
        } else {
            addFavorite(article)
            true
        }
    }

    fun getFavorites(): List<Article> {
        val key = getUserKey(KEY_FAVORITES_PREFIX)
        val json = prefs.getString(key, null) ?: return emptyList()
        val type = object : TypeToken<List<Article>>() {}.type
        return try {
            gson.fromJson(json, type)
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun saveFavorites(favorites: List<Article>) {
        val key = getUserKey(KEY_FAVORITES_PREFIX)
        val json = gson.toJson(favorites)
        prefs.edit().putString(key, json).apply()
    }

    // ==================== READ LATER (Per-User) ====================

    fun addReadLater(article: Article) {
        val readLater = getReadLater().toMutableList()
        if (readLater.none { it.url == article.url }) {
            readLater.add(article)
            saveReadLater(readLater)
        }
    }

    fun removeReadLater(article: Article) {
        val readLater = getReadLater().toMutableList()
        readLater.removeAll { it.url == article.url }
        saveReadLater(readLater)
    }

    fun isReadLater(url: String): Boolean {
        return getReadLater().any { it.url == url }
    }

    fun toggleReadLater(article: Article): Boolean {
        return if (isReadLater(article.url)) {
            removeReadLater(article)
            false
        } else {
            addReadLater(article)
            true
        }
    }

    fun getReadLater(): List<Article> {
        val key = getUserKey(KEY_READ_LATER_PREFIX)
        val json = prefs.getString(key, null) ?: return emptyList()
        val type = object : TypeToken<List<Article>>() {}.type
        return try {
            gson.fromJson(json, type)
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun saveReadLater(readLater: List<Article>) {
        val key = getUserKey(KEY_READ_LATER_PREFIX)
        val json = gson.toJson(readLater)
        prefs.edit().putString(key, json).apply()
    }

    // ==================== USER PREFERENCES (Per-User) ====================

    fun saveUserPreferences(categories: Set<NewsCategory>) {
        val key = getUserKey(KEY_PREFERENCES_PREFIX)
        val categoryNames = categories.map { it.name }
        val json = gson.toJson(categoryNames)
        prefs.edit().putString(key, json).apply()
    }

    fun getUserPreferences(): Set<NewsCategory> {
        val key = getUserKey(KEY_PREFERENCES_PREFIX)
        val json = prefs.getString(key, null) ?: return getDefaultPreferences()
        val type = object : TypeToken<List<String>>() {}.type
        return try {
            val categoryNames: List<String> = gson.fromJson(json, type)
            categoryNames.mapNotNull { name ->
                try { NewsCategory.valueOf(name) } catch (e: Exception) { null }
            }.toSet()
        } catch (e: Exception) {
            getDefaultPreferences()
        }
    }

    private fun getDefaultPreferences(): Set<NewsCategory> {
        // Default to all categories if none selected
        return setOf(NewsCategory.TECHNOLOGY, NewsCategory.BUSINESS, NewsCategory.WORLD, NewsCategory.ECONOMY)
    }

    fun getPreferredCategories(): List<String> {
        return getUserPreferences().map { it.apiValue }.distinct()
    }
}
