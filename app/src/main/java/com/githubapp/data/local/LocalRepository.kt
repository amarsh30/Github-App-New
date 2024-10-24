package com.githubapp.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.githubapp.data.local.dao.UserDao
import com.githubapp.data.local.database.UserDatabase
import com.githubapp.data.local.entity.UserFavoriteEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabaseUser(application)
        mUserDao = db.userDao()
    }

    fun getAll(): LiveData<List<UserFavoriteEntity>> = mUserDao.getAllUsers()

    fun insertUser(login: UserFavoriteEntity) {
        executorService.execute { mUserDao.insertUsers(login) }
    }

    fun delete(login: UserFavoriteEntity) {
        executorService.execute { mUserDao.deleteUsers(login) }
    }

    fun getFavoriteUser(login: String): LiveData<UserFavoriteEntity> {
        return mUserDao.getFavoriteUsers(login)
    }

}