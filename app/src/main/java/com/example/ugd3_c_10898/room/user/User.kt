package com.example.ugd3_c_10898.room.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val username : String,
    val password : String,
    val email : String,
    val tglLahir : String,
    val noHp : Int
)