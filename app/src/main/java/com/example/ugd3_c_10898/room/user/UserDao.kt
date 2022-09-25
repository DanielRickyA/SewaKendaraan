package com.example.ugd3_c_10898.room.user

import androidx.room.*

@Dao
interface UserDao {
    @Insert
     fun addUser(user : User)

    @Update
     fun updateUser(user: User)

    @Delete
     fun deleteUser(user: User)

     @Query("SELECT * FROM user WHERE id =:user_id")
     fun getUser(user_id: Int) : List<User>

     @Query("SELECT * FROM user")
     fun getUsers() : List<User>

     @Query("SELECT * FROM user WHERE username =:username & password =:passowrd")
     fun getUsersByUsername(username: String, passowrd: String) : List<User>
}