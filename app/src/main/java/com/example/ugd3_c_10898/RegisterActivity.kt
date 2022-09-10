package com.example.ugd3_c_10898

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

class RegisterActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputTanggalLahir: TextInputLayout
    private lateinit var inputNoTelp: TextInputLayout
    private lateinit var registerLayout: ConstraintLayout

//    private DatePickerDialog datePickerDialog;
//    private SimpleDateFormat dateFormatter;
//    private TextView tvDateResult;
//    private Button btDatePicker;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setTitle("Register Cycle Fast")
        inputUsername = findViewById(R.id.inputUsername2)
        inputPassword = findViewById(R.id.inputPassword2)
        inputEmail = findViewById(R.id.inputEmail)
        inputTanggalLahir = findViewById(R.id.inputTL)
        inputNoTelp = findViewById(R.id.inputNoTelp)
        registerLayout = findViewById(R.id.registerLayout)
        val btnActionRegister : Button = findViewById(R.id.btnActionRegister)

        btnActionRegister.setOnClickListener(View.OnClickListener {
            var actionRegister = false

            val username : String = inputUsername.getEditText()?.getText().toString()
            val password : String = inputUsername.getEditText()?.getText().toString()
            val email : String = inputEmail.getEditText()?.getText().toString()
//            val tgl : String = inputTanggalLahir.getEditText()?.getText()
        })
    }
}