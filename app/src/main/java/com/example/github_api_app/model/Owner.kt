package com.example.github_api_app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Owner (
    @SerializedName("login") @Expose val user: String,
    val avatar_url: String,
    val type: String
        ) : Serializable