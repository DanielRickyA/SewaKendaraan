package com.example.ugd3_c_10898.entity

import com.example.ugd3_c_10898.R

class Motor (var merkMotor: String, var namaMotor: String, var imageMotor: Int){
    companion object{
        var listOfMotor = arrayOf(
            Motor("Honda", "Frederik Hayodi", R.drawable.icon_motor_honda1),
            Motor("Honda", "Anita Winoto", R.drawable.icon_motor_honda2),
            Motor("Honda", "Dandy Febri Kusuma", R.drawable.icon_motor_honda3),
            Motor("Honda", "Reza Adi Pamungkas", R.drawable.icon_motor_honda4),
            Motor("Yamaha", "Jefrey Sanjaya", R.drawable.icon_motor_yamaha),
            Motor("Yamaha", "Michael Alexander", R.drawable.icon_motor_yamaha2),
            Motor("Suzuki", "Agung Manggiri", R.drawable.icon_motor_suzuki1),
            Motor("Suzuki", "Putri Dian", R.drawable.icon_motor_suzuki2),
            Motor("Kawasaki", "Maria Christina", R.drawable.icon_motor_kawasaki),
            Motor("Kawasaki", "Christopher Mathew", R.drawable.icon_motor_kawasaki2),
        )
    }
}