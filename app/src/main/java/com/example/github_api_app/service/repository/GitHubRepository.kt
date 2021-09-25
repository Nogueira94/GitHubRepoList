package com.example.github_api_app.service.repository

import com.example.github_api_app.service.service.GitHubService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val gitHubAPI = Retrofit.Builder()
    .baseUrl(" https://api.github.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val gitHubService = gitHubAPI.create(GitHubService::class.java)