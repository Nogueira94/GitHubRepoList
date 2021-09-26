package com.example.github_api_app.model

data class SearchResult<T> (
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: ArrayList<T>
)