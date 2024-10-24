package com.githubapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.githubapp.data.local.LocalRepository
import com.githubapp.data.local.entity.UserFavoriteEntity

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mLocalRepository: LocalRepository = LocalRepository(application)

    fun getAllUsers(): LiveData<List<UserFavoriteEntity>> = mLocalRepository.getAll()

    fun insertUser(login: UserFavoriteEntity) {
        mLocalRepository.insertUser(login)
    }

    fun deleteUser(login: UserFavoriteEntity) {
        mLocalRepository.delete(login)
    }

    fun getFavoriteUser(login: String): LiveData<UserFavoriteEntity> {
        return mLocalRepository.getFavoriteUser(login)
    }
}

