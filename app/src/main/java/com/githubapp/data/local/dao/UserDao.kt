package com.githubapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.githubapp.data.local.entity.UserFavoriteEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM UserFavoriteEntity WHERE login = :login ")
    fun getFavoriteUsers(login: String): LiveData<UserFavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(login: UserFavoriteEntity)

    @Delete
    fun deleteUsers(login: UserFavoriteEntity)

    @Query("SELECT * FROM UserFavoriteEntity")
    fun getAllUsers(): LiveData<List<UserFavoriteEntity>>
}