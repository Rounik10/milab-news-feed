package com.rounik.milab.newsfeed.api

import com.rounik.milab.newsfeed.model.NewsApiResponse
import retrofit2.http.GET

interface NewsService {
    @GET("top-headlines?country=in&apiKey=df58f56ad7604d5a9d33e42e9e880944")
    suspend fun getHeadlines(): NewsApiResponse
}