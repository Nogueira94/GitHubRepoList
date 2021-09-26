package com.example.github_api_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.github_api_app.model.Item
import com.example.github_api_app.service.repository.GitHubRepoImpl
import com.example.github_api_app.service.repository.gitHubService
import kotlinx.coroutines.flow.Flow

class RepositoriesViewModel() : ViewModel() {

    private val service = gitHubService

    private val repository : GitHubRepoImpl = GitHubRepoImpl(service)
    private var currentResult: Flow<PagingData<Item>>? = null

    fun getAllRepo(s: String) : Flow<PagingData<Item>>{
        currentResult = repository.getRepositoriesByStar(s)
        return currentResult as Flow<PagingData<Item>>
    }

}
