package com.example.github_api_app.view.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.GONE
import android.view.ViewParent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.utils.ViewState
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_api_app.databinding.ActivityHomeBinding
import com.example.github_api_app.service.adapter.GitRepoLoadStateAdapter
import com.example.github_api_app.utils.NetworkState
import com.example.github_api_app.view.home.adapter.GitRepoAdapter
import com.example.github_api_app.view.home.utils.GitHubView
import com.example.github_api_app.viewmodel.RepositoriesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.zip.GZIPOutputStream


class HomeActivity : GitHubView, AppCompatActivity() {

    init {
        first_req = true
    }

    companion object{
        var first_req = true
    }

    private lateinit var viewModel : RepositoriesViewModel
    private lateinit var gitRepoAdapter : GitRepoAdapter
    private lateinit var bind: ActivityHomeBinding
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        val view = bind.root
        supportActionBar?.hide()
        gitRepoAdapter = GitRepoAdapter(this)
        viewModel = RepositoriesViewModel(this)
        setContentView(view)

        checkNetwork(NetworkState.checkForInternet(this))

    }

    private fun checkNetwork(value : Boolean) {
        if(value) return startView()
        hideProgress()
        startNoNetworkView()
    }

    private fun showProgress(){
        bind.progressBarContainer.visibility = View.VISIBLE

    }


    private fun startView() {
        showProgress()
        if(bind.noconnectionContainer.isVisible){
            bind.noconnectionContainer.visibility = View.GONE
        }
        loadData("language:kotlin")
        setRecyclerView()
    }


    private fun startNoNetworkView(){
        hideProgress()
        bind.noconnectionContainer.visibility = View.VISIBLE
        bind.retryButton.setOnClickListener {
            checkNetwork(NetworkState.checkForInternet(this))
        }
    }

    private fun setRecyclerView() {
        bind.rvGitRepo.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = gitRepoAdapter.withLoadStateHeaderAndFooter(
                header = GitRepoLoadStateAdapter(gitRepoAdapter),
                footer = GitRepoLoadStateAdapter(gitRepoAdapter)
            )
            addItemDecoration(DividerItemDecoration(applicationContext,DividerItemDecoration.VERTICAL))
        }
    }

    private fun loadData(s: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch { viewModel.getAllRepo(s).collect { gitRepoAdapter.submitData(it) }}
    }

    override fun showRetryBtn() {
        try {
            bind.progressBarContainer.visibility = View.GONE
            bind.noconnectionContainer.visibility = View.VISIBLE
            bind.retryButton.setOnClickListener {
                checkNetwork(NetworkState.checkForInternet(this))
            }
        }catch (e:Exception){
            Toast.makeText(this, "ERROR, tente novamente", Toast.LENGTH_SHORT).show()
        }
    }

    override fun hideProgress() {
        try {
            bind.progressBarContainer.visibility = GONE
        } catch (e: Exception){
            Toast.makeText(this, "ERROR, tente novamente", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getFragManager() : FragmentManager {
        return supportFragmentManager
    }



}