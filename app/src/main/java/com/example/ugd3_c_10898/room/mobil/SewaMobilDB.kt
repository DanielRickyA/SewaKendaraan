//package com.example.ugd3_c_10898.room.mobil
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//@Database(
//    entities = [SewaMobil::class],
//    version = 1
//)
//abstract class SewaMobilDB: RoomDatabase() {
//    abstract fun SewaMobilDao(): SewaMobilDao
//
//    companion object {
//        @Volatile
//        private var instance: SewaMobilDB? = null
//        private val LOCK = Any()
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: buildDatabase(context).also {
//                instance = it
//            }
//        }
//
//        private fun buildDatabase(context: Context) = Room.databaseBuilder(
//            context.applicationContext, SewaMobilDB::class.java, "user12345.db"
//        ).build()
//    }
//}