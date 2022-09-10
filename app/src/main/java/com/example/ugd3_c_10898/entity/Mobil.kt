package com.example.ugd3_c_10898.entity

import com.example.ugd3_c_10898.R

class Mobil (var merk: String, var nama: String, var image: Int) {
    companion object{
        var listOfMobil = arrayOf(
            Mobil("Toyota", "Bagus Citra Diputra" , R.drawable.icon_toyota),
            Mobil("Toyota", "Alexander Dendy Kurniawan" , R.drawable.icon_toyota2),
            Mobil("Toyota", "Bimo Gunawan" , R.drawable.icon_toyota3),
            Mobil("Daihatsu", "Hartono Setiawan" , R.drawable.icon_daihatsu1),
            Mobil("Daihatsu", "kevin Sanjaya" , R.drawable.icon_daihatsu2),
            Mobil("Daihatsu", "Antony Wijaya" , R.drawable.icon_daihatsu3),
            Mobil("Honda", "Jotaro Joestar" , R.drawable.icon_honda),
            Mobil("Honda", "Satria Baja Hitam" , R.drawable.icon_honda2),
            Mobil("Honda", "Wiliam Edward" , R.drawable.icon_honda3),
            Mobil("Lamborgini", "Valent Kurniawan" , R.drawable.icon_lamborgini)
        )
    }
}