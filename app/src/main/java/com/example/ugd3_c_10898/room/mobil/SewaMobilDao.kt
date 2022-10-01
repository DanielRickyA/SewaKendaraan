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

    @Query("SELECT * FROM SewaMobil WHERE id =:sewaMobil_id")
    suspend fun getSewaMobil(sewaMobil_id: Int) : List<SewaMobil>
}