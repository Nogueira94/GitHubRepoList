package com.example.github_api_app.service.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.github_api_app.service.service.GitHubService

class GitHubRepoImpl ( private val api: GitHubService) {
    fun getRepositoriesByStar(s: String) =
        Pager(
            pagingSourceFactory = {GitRepoPaging(s,api)},
            config = PagingConfig(
                pageSize = 30,
                maxSize = 1000
            )
        ).flow

}