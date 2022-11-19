package com.example.ugd3_c_10898.api

class TubesApi {
    companion object{
        val BASE_URL = "http://127.0.0.1:8000/android-apiserver/public/" //Kalo Mo nyoba ganti link nya aja

        val register = BASE_URL + "register"
        var login = BASE_URL + "login"

        val getAllSewa = BASE_URL + "SewaKendaraan"
        val getByIdSewa = BASE_URL + "SewaKendaraan/"
        val createSewa = BASE_URL + "SewaKendaraan"
        val updateSewa = BASE_URL + "SewaKendaraan/"
        val deleteSewa = BASE_URL + "SewaKendaraan/"
    }
}