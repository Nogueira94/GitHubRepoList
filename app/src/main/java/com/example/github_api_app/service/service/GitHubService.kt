package com.example.github_api_app.service.service

import com.example.github_api_app.model.Item
import com.example.github_api_app.model.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {

    @GET("search/repositories")
    suspend fun getRepositoriesByStar(
        @Query("q") queryParams : String,
        @Query("sort") sort: String = "stars",
        @Query("page") page: Int
    ) : Response<SearchResult<Item>>

}