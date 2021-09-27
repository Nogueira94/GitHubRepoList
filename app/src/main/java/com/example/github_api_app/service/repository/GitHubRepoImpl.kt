package com.example.github_api_app.service.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.github_api_app.service.service.GitHubService
import com.example.github_api_app.view.home.utils.GitHubView

class GitHubRepoImpl ( private val api: GitHubService, private val view : GitHubView) {
    fun getRepositoriesByStar(s: String) =
        Pager(
            pagingSourceFactory = {GitRepoPaging(s,api,view)},
            config = PagingConfig(
                pageSize = 30,
                maxSize = 1000
            )
        ).flow

}