package com.example.ugd3_c_10898.room.mobil

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SewaMobil (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val lokasi : String,
    val tanggalPinjam : String,
    val tanggalKembali : String,
    val modelKendaraan : String
)