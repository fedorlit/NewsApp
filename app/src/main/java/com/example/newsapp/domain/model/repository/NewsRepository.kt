package com.example.newsapp.domain.model.repository

import com.example.newsapp.domain.model.Article
import com.example.newsapp.util.Resource

interface NewsRepository {

    suspend fun getTopHeadlines(
        category: String
    ):Resource<List<Article>>

}