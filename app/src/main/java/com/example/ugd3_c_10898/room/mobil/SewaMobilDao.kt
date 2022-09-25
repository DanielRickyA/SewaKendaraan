package com.example.ugd3_c_10898.room.mobil

import androidx.room.*

@Dao
interface SewaMobilDao {
    @Insert
    suspend fun addUser(sewaMobil: SewaMobil)

    @Update
    suspend fun updateUser(sewaMobil: SewaMobil)

    @Delete
    suspend fun deleteUser(sewaMobil: SewaMobil)

    @Query("SELECT * FROM SewaMobil WHERE id =:sewaMobil_id")
    suspend fun getSewaMobil(sewaMobil_id: Int) : List<SewaMobil>
}