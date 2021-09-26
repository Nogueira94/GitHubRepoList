package com.example.github_api_app.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github_api_app.databinding.CardGitrepoBinding
import com.example.github_api_app.model.Item
import com.example.github_api_app.view.home.HomeActivity
import com.example.github_api_app.view.home.dialog.RepoDialog


class GitRepoAdapter : PagingDataAdapter<Item,GitRepoAdapter.GitRepoViewHolder>(DIFF_UTILL) {

    companion object {
        val DIFF_UTILL = object  : DiffUtil.ItemCallback<Item>(){
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoViewHolder {
        val bind = CardGitrepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GitRepoViewHolder(bind)
    }

    override fun onBindViewHolder(holder: GitRepoViewHolder, position: Int) {
        val repo = getItem(position)
        Glide.with(holder.bind.ivRepoImage.context).asBitmap()
            .load(repo?.owner?.avatar_url)
            .into(holder.bind.ivRepoImage)
        holder.bind.tvRepoName.text = repo?.name
        holder.bind.tvQtFork.text = repo?.forks.toString()
        holder.bind.tvQtStars.text = repo?.stars.toString()
        holder.bind.tvAutorName.text = repo?.owner?.user

        holder.bind.root.setOnClickListener {
            val dialog = RepoDialog(repo!!)
            dialog.show(HomeActivity.getFragmentManager(),"DIALOG SHOW")
        }

    }

    inner class GitRepoViewHolder(val bind: CardGitrepoBinding) : RecyclerView.ViewHolder(bind.root)

}