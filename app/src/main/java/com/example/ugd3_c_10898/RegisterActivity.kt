package com.example.ugd3_c_10898

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.example.ugd3_c_10898.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.ugd3_c_10898.room.user.User
import com.example.ugd3_c_10898.room.user.UserDB
import com.example.ugd3_c_10898.room.Constant
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var username : TextInputEditText
    private lateinit var password : TextInputEditText
//    private lateinit var btnRegister : Button

    // Code Room untuk Users
//    val db by lazy { UserDB(this) }
//    private var userId: Int = 0
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register)
//        setupView()
//        setupListener()
//        Toast.makeText(this, userId.toString(), Toast.LENGTH_SHORT).show()
//    }
//
//    fun setupView(){
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        val intentType = intent.getIntExtra("intent_type", 0)
//        when (intentType){
//            Constant.TYPE_CREATE -> {
//                btnActionRegister.visibility = View.GONE
//            }
//        }
//    }
//    private fun setupListener() {
//        btnActionRegister.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                db.userDao().addUser(
//                    User(0,etUsername.text.toString(), etPassword.text.toString(),  etEmail.text.toString(),  inputTL.text.toString(), inputNoTelp.number.toInt())
//                )
//                finish()
//            }
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        getSupportActionBar()?.hide()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle("Register Cycle Fast")
//        var view = inflater.inflater()
        username = findViewById(R.id.etUsername)
        password = findViewById(R.id.etPassword)

        binding.btnActionRegister.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            val mBundle = Bundle()
            mBundle.putString("username",username.text.toString())
            mBundle.putString("password",password.text.toString())
            intent.putExtra("register", mBundle)

            startActivity(intent)
        }
    }
}