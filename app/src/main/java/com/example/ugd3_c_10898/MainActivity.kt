package com.example.ugd3_c_10898

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var registerLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Title
        setTitle("Login Cycle Fast")

        inputUsername = findViewById(R.id.inputUsername)
        inputPassword = findViewById(R.id.inputPassword)
        mainLayout = findViewById(R.id.loginLayout)
        val btnRegister : Button = findViewById(R.id.btnRegister)
        val btnLogin : Button= findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            if (username.isEmpty()) {
                inputUsername.setError("Username Must be Filled With Text")
                checkLogin = false
            }
            if (password.isEmpty()) {
                inputPassword.setError("Password Must be Filled With Text")
                checkLogin = false
            }

            if(username == "admin" && password == "0898") checkLogin = true

            if(!checkLogin)
                return@OnClickListener

            val moveHome = Intent ( this@MainActivity, HomeActivity::class.java)
            startActivity(moveHome)
        })

        btnRegister.setOnClickListener(View.OnClickListener {

            val moveRegister = Intent ( this@MainActivity, RegisterActivity::class.java)
            startActivity(moveRegister)
        })
    }
}