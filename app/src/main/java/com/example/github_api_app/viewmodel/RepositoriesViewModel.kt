package com.example.github_api_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.github_api_app.service.repository.GitRepoPaging
import com.example.github_api_app.service.repository.gitHubService

class RepositoriesViewModel() : ViewModel() {

    private val service = gitHubService
    private val list = MutableLiveData("")
    val repoList = list.switchMap {
        Pager(PagingConfig(pageSize = 30, maxSize = 100000)) {
            GitRepoPaging(it,service)
        }.liveData.cachedIn(viewModelScope)
    }

    fun getRepoForLang(s: String){
        list.postValue(s)
    }

}