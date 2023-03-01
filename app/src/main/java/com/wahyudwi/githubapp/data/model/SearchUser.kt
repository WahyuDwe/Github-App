package com.wahyudwi.githubapp.data.model

import com.google.gson.annotations.SerializedName

data class SearchUser(
    @SerializedName("id")
    val id: Int,

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)
