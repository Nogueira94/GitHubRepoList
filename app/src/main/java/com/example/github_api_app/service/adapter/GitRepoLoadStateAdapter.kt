package com.example.github_api_app.service.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.github_api_app.R
import com.example.github_api_app.databinding.ItemLoadBinding
import com.example.github_api_app.service.repository.GitRepoPaging
import com.example.github_api_app.view.home.adapter.GitRepoAdapter
import java.net.UnknownHostException

class GitRepoLoadStateAdapter (
    private val adapter: GitRepoAdapter
        ) : LoadStateAdapter<GitRepoLoadStateAdapter.LoadStateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,loadState: LoadState) =
        LoadStateItemViewHolder(
            ItemLoadBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_load, parent, false)
            )
        ) { adapter.retry() }

    override fun onBindViewHolder(holder: LoadStateItemViewHolder, loadState: LoadState) = holder.bind(loadState)

    class LoadStateItemViewHolder (
        private val bind : ItemLoadBinding,
        private val retry : () -> Unit
    ) : RecyclerView.ViewHolder(bind.root) {

        init {
            bind.retryButton.setOnClickListener { retry() }
        }

        fun bind (load: LoadState){
            with(bind){
                progressBar.isVisible = load is LoadState.Loading
                retryButton.isVisible = load is LoadState.Error
                errorMsg.isVisible =
                    !(load as? LoadState.Error)?.error?.message.isNullOrBlank()
                when((load as? LoadState.Error)?.error){
                    is GitRepoPaging.APIResponseERROR -> errorMsg.setText(R.string.api_response_limit)
                    is UnknownHostException -> errorMsg.setText(R.string.no_internet_connection)
                    else -> errorMsg.setText(R.string.generic_error)
                }
            }
        }
    }
}