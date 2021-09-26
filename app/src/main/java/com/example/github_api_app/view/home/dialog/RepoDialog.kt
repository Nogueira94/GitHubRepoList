package com.example.github_api_app.view.home.dialog

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.github_api_app.R
import com.example.github_api_app.databinding.DialogRepositoryBinding
import com.example.github_api_app.model.Item

class RepoDialog (private val item: Item) : DialogFragment() {

    lateinit var bind: DialogRepositoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DialogRepositoryBinding.inflate(inflater, container, false)
        getDialog()?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        setValues()
        setListeners()
        return bind.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) this.dismiss()
    }

    private fun setListeners() {
        bind.btnWeb.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(item.repoUrl))
            startActivity(i)
        }
        bind.btnShare.setOnClickListener {
            val i = Intent()
            i.action = Intent.ACTION_SEND
            i.type="text/plain"
            i.putExtra(Intent.EXTRA_TEXT, " Veja o projeto ${item.full_name} : ${item.repoUrl}")
            startActivity(i)
        }
        bind.btnFecharPopup.setOnClickListener {
            this.dismiss()
        }
    }

    private fun setValues() {
        bind.tvTituloPopup.text = item.full_name
        bind.tvAutorName.text = item.owner.user
        if(!item.desc.isNullOrBlank() || !item.desc.isNullOrEmpty()){
            bind.tvRepoDesc.text = item.desc
        } else {
            bind.tvRepoDesc.setText(R.string.without_desc)
        }
        bind.tvType.text = item.owner.type
        bind.tvRepoName.text = item.name
        bind.tvQtStars.text = item.stars.toString()
        bind.tvQtFork.text = item.forks.toString()
        Glide.with(bind.ivRepoImage.context).asBitmap()
            .load(item.owner.avatar_url)
            .into(bind.ivRepoImage)
    }

}