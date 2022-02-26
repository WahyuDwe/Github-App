package com.wahyudwi.githubapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wahyudwi.githubapp.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favoriteentities")
    fun getFavoritedUser(): LiveData<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT count(*) FROM favoriteentities WHERE favoriteentities.id_user = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM favoriteentities WHERE favoriteentities.id_user = :id")
    fun deleteFavorite(id: Int): Int
}
