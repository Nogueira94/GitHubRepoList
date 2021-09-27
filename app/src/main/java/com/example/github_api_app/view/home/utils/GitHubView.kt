package com.example.github_api_app.view.home.utils

import androidx.fragment.app.FragmentManager

interface GitHubView {
    fun showRetryBtn()
    fun hideProgress()
    fun getFragManager() : FragmentManager
}