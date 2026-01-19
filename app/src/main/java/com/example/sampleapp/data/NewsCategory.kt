package com.example.sampleapp.data

enum class NewsCategory(val displayName: String, val apiValue: String) {
    TECHNOLOGY("Technology", "technology"),
    BUSINESS("Business", "business"),
    WORLD("World", "general"),
    ECONOMY("Economy", "business");

    companion object {
        fun fromApiValue(value: String): NewsCategory? {
            return values().find { it.apiValue == value }
        }
        
        fun fromDisplayName(name: String): NewsCategory? {
            return values().find { it.displayName == name }
        }
    }
}
