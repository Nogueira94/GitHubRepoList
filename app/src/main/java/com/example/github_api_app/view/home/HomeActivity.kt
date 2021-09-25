package com.example.github_api_app.view.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_api_app.databinding.ActivityHomeBinding
import com.example.github_api_app.view.home.adapter.GitRepoAdapter
import com.example.github_api_app.viewmodel.RepositoriesViewModel


class HomeActivity : AppCompatActivity() {

    private val viewModel : RepositoriesViewModel by viewModels()
    private val gitRepoAdapter : GitRepoAdapter = GitRepoAdapter()
    private lateinit var bind: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        val view = bind.root
        supportActionBar?.hide()
        setContentView(view)

        setRecyclerView()

        viewModel.repoList.observe(this){
            gitRepoAdapter.submitData(lifecycle,it)
        }

    }

    private fun setRecyclerView() {

        bind.rvGitRepo.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = gitRepoAdapter
            addItemDecoration(DividerItemDecoration(applicationContext,DividerItemDecoration.VERTICAL))
        }
        loadData()
    }

    private fun loadData() {
        viewModel.getRepoForLang("language:kotlin")
    }


}