package com.wahyudwi.githubapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteentities")
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_user")
    val id: Int,

    @ColumnInfo(name = "username")
    val login: String,

    @ColumnInfo(name = "image_path")
    val avatar_url: String
)
