package com.rounik.milab.newsfeed.model

data class NewsApiResponse(
    val status: String,
    val articles: List<News>
)