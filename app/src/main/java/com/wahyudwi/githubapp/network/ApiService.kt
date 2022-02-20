package com.wahyudwi.githubapp.network

import com.wahyudwi.githubapp.BuildConfig
import com.wahyudwi.githubapp.data.model.DetailUserResponse
import com.wahyudwi.githubapp.data.model.SearchUser
import com.wahyudwi.githubapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
    ): Call<ArrayList<SearchUser>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<SearchUser>>
}
