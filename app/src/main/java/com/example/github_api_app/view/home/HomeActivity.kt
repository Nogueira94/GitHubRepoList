package com.example.github_api_app.view.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_api_app.databinding.ActivityHomeBinding
import com.example.github_api_app.service.adapter.GitRepoLoadStateAdapter
import com.example.github_api_app.utils.NetworkState
import com.example.github_api_app.view.home.adapter.GitRepoAdapter
import com.example.github_api_app.viewmodel.RepositoriesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity() {

    init {
        instance = this
    }

    companion object{
        private var instance: HomeActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    private val viewModel : RepositoriesViewModel by viewModels()
    private val gitRepoAdapter : GitRepoAdapter = GitRepoAdapter()
    lateinit var bind: ActivityHomeBinding
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        val view = bind.root
        supportActionBar?.hide()
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

    private fun hideProgress(){
        Handler().postDelayed({
            bind.progressBarContainer.visibility = View.GONE
        },2000)
    }

    private fun startView() {
        if(bind.noconnectionContainer.isVisible){
            bind.noconnectionContainer.visibility = View.GONE
        }
        loadData("language:kotlin")
        setRecyclerView()
        hideProgress()
    }


    private fun startNoNetworkView(){
        bind.noconnectionContainer.visibility = View.VISIBLE
        bind.retryButton.setOnClickListener {
            checkNetwork(NetworkState.checkForInternet(this))
            showProgress()
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

}