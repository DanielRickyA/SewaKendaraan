package com.example.ugd3_c_10898.api

class TubesApi {
    companion object{
        val BASE_URL = "http://192.168.19.48:8081/android-apiserver/public/api/" //Kalo Mo nyoba ganti link nya aja

        val register = BASE_URL + "register"
        val login = BASE_URL + "login"
        var getUserId = BASE_URL + "user/"

        val getAllSewa = BASE_URL + "SewaKendaraan"
        val getByIdSewa = BASE_URL + "SewaKendaraan/"
        val createSewa = BASE_URL + "SewaKendaraan"
        val updateSewa = BASE_URL + "SewaKendaraan/"
        val deleteSewa = BASE_URL + "SewaKendaraan/"
    }
}