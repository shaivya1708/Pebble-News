package com.example.sampleapp.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: Date,
    val content: String?
) : Parcelable

@Parcelize
data class Source(
    val id: String?,
    val name: String
) : Parcelable