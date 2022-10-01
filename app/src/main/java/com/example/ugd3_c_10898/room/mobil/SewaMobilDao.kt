package com.example.ugd3_c_10898.room.mobil

import androidx.room.*
import com.example.ugd3_c_10898.room.user.User

@Dao
interface SewaMobilDao {
    @Insert
    fun addSewaMobil(sewaMobil: SewaMobil)

    @Update
    fun updateSewaMobil(sewaMobil: SewaMobil)

    @Delete
    fun deleteSewaMobil(sewaMobil: SewaMobil)

    @Query("SELECT * FROM sewaMobil WHERE id =:sewaMobil_id")
    fun getDataSewaMobil(sewaMobil_id: Int) : SewaMobil

    @Query("Select * FROM sewamobil")
    fun getAllData(): List<SewaMobil>
}