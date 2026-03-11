package com.example.newsapp.data.remote

import com.example.newsapp.domain.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    //GET https://newsapi.org/v2/top-headlines?country=us&apiKey=9f0a55fc13b940c48259fa4255addf16

    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("category") category: String,
        @Query("country") country: String="us",
        @Query("apiKey") apiKey: String= API_KEY
    ): NewsResponse

    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "9f0a55fc13b940c48259fa4255addf16"
    }
}