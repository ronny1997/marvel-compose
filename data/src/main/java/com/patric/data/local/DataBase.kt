package com.patric.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.patric.data.local.model.User
import com.patric.data.local.model.UserDao
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@Database(entities = [User::class], version = 1)
abstract class AppDatabase () : RoomDatabase() {
    abstract fun userDao(): UserDao
}