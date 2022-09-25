package com.example.ugd3_c_10898

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

import com.example.ugd3_c_10898.databinding.ActivityRegisterBinding

import com.google.android.material.textfield.TextInputEditText


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

//     Code Room untuk Users
    val db by lazy { UserDB(this) }
    private var userId: Int = 0

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                btnActionRegister.visibility = View.GONE
            }
        }
    }
    private fun setupListener() {

        db.userDao().addUser(
            User(0,binding.etUsername.text.toString(), binding.etPassword.text.toString(),  binding.etEmail.text.toString(),
                binding.inputTL.text.toString(), (binding.inputNoTelp.editText?.text.toString()).toInt())
        )

    }
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
            setupListener()
//            println(db.userDao().getUsers())
            val intent = Intent(this, MainActivity::class.java)
            val mBundle = Bundle()
            mBundle.putString("username",username.text.toString())
            mBundle.putString("password",password.text.toString())
            intent.putExtra("register", mBundle)

            startActivity(intent)
        }
    }
}