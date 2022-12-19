package com.example.ugd3_c_10898.api

class TubesApi {
    companion object{
        val BASE_URL = "https://dev.ksaduajy.com/adroid-apiserver/api/"

        val register = BASE_URL + "register"
        val login = BASE_URL + "login"
        val getUserId = BASE_URL + "user/"
        val updateUser = BASE_URL + "user/"

        val getAllSewa = BASE_URL + "SewaKendaraan"
        val getByIdSewa = BASE_URL + "SewaKendaraan/"
        val createSewa = BASE_URL + "SewaKendaraan"
        val updateSewa = BASE_URL + "SewaKendaraan/"
        val deleteSewa = BASE_URL + "SewaKendaraan/"

        val getAllSewaMotor = BASE_URL + "SewaMotor"
        val getByIdSewaMotor = BASE_URL + "SewaMotor/"
        val createSewaMotor = BASE_URL + "SewaMotor"
        val updateSewaMotor = BASE_URL + "SewaMotor/"
        val deleteSewaMotor = BASE_URL + "SewaMotor/"

        val getAllKritikSaran = BASE_URL + "KritikSaran"
        val getByIDKritikSaran = BASE_URL + "KritikSaran/"
        val createKritikSaran = BASE_URL + "KritikSaran"
        val updateKritikSaran = BASE_URL + "KritikSaran/"
        val deleteKritikSaran = BASE_URL + "KritikSaran/"
    }
}