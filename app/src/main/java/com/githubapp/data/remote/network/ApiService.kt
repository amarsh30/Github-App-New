package com.githubapp.data.remote.network

import com.githubapp.data.remote.response.ApiResponse
import retrofit2.Call
import retrofit2.http.*
import com.githubapp.data.remote.response.UserResponse

interface ApiService {
    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<ApiResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<UserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<UserResponse>>
}