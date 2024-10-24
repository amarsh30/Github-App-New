package com.githubapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.githubapp.data.local.dao.UserDao
import com.githubapp.data.local.entity.UserFavoriteEntity

@Database(entities = [UserFavoriteEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        @JvmStatic
        fun getDatabaseUser(context: Context): UserDatabase {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java, "db.user"
                    )
                        .build()
                }
            }
            return INSTANCE as UserDatabase
        }
    }
}