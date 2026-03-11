package com.example.newsapp.presentation.theme.news_screen

import com.example.newsapp.domain.model.Article

sealed class NewsScreenEvent{
    data class OnNewsCardClicked(val article: Article):NewsScreenEvent()
    data class OnCategoryChanged(val category: String): NewsScreenEvent()
    data class OnSearchQueryChanged(val searchQuery: String):NewsScreenEvent()
    object OnSearchIconClicked:NewsScreenEvent()
    object OnCloseIconClicked:NewsScreenEvent()
}