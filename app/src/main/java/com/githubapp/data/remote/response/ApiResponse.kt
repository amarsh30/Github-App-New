package com.githubapp.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class ApiResponse(
    @field:SerializedName("items")
    val items: List<UserResponse>
)

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class UserResponse(

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("name")
    val name: String,

) : Parcelable