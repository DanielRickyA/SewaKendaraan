package com.example.ugd3_c_10898.room.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ugd3_c_10898.room.mobil.SewaMobil
import com.example.ugd3_c_10898.room.mobil.SewaMobilDao

@Database(
    entities = [User::class , SewaMobil::class],
    version = 1
)
abstract class UserDB: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun SewaMobilDao(): SewaMobilDao

    companion object {
        @Volatile
        private var instance: UserDB? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, UserDB::class.java, "user12345.db"
        ).allowMainThreadQueries().build()
    }
}