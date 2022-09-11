package com.example.ugd3_c_10898

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

class RegisterActivity : AppCompatActivity() {
    private lateinit var username : TextInputEditText
    private lateinit var password : TextInputEditText
    private lateinit var btnRegister : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setTitle("Register Cycle Fast")

        username = findViewById(R.id.etUsername)
        password = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            val mBundle = Bundle()
            mBundle.putString("username",username.text.toString())
            mBundle.putString("password",password.text.toString())
            intent.putExtra("register", mBundle)

            startActivity(intent)
        }
    }
}