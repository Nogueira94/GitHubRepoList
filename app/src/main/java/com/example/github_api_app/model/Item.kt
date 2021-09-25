package com.example.github_api_app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.w3c.dom.CharacterData
import java.io.Serializable

data class Item (
    val id : Int,
    val name: String,
    val full_name: String,
    val owner: Owner,
    @SerializedName("html_url") @Expose val repoUrl: String,
    @SerializedName("forks_count") @Expose val forks: Int,
    @SerializedName("stargazers_count") @Expose val stars: Int
        ) : Serializable