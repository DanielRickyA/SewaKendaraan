package com.example.ugd3_c_10898

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ugd3_c_10898.databinding.ActivityMainBinding
import com.example.ugd3_c_10898.room.user.UserDB
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_kendaraan.view.*

class MainActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    private lateinit var binding: ActivityMainBinding
    lateinit var  mBundle: Bundle
    var pref: SharedPreferences? = null
    var vUsername : String = ""
    var vPassword : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.hide()
//        getBundle()
        pref = getSharedPreferences("prefId", Context.MODE_PRIVATE)
        setTitle("User Login")
        //bindingnya
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//      tampungan dari register untuk main
        if(intent.getBundleExtra("register")!=null){
            getBundle()
        }


//      untuk pindah ke register
        binding.btnRegister.setOnClickListener{
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

//      untuk clear
        binding.btnClear.setOnClickListener{
            binding.inputUsername.editText!!.setText("")
            binding.inputPassword.editText!!.setText("")

            Snackbar.make(binding.loginLayout, "Text Berhasil Dihapus",Snackbar.LENGTH_LONG).show()
        }

//      ini buat login
        binding.btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin=false
            val username:String=binding.inputUsername.getEditText()?.getText().toString()
            val password:String=binding.inputPassword.getEditText()?.getText().toString()

            val user = db.userDao().getUsersByUsername(username,password)

            if( user != null ){
                checkLogin=true
                val edit : SharedPreferences.Editor = pref!!.edit()
                edit.putInt("id",user.id)
                edit.apply()
            }else{
                binding.inputUsername.setError("Username Salah")
                binding.inputPassword.setError("Password Salah")
            }

            if(checkLogin){
                val moveHome=Intent(this@MainActivity,HomeActivity::class.java)
                startActivity(moveHome)
                finish()
            }

        })
//      ini buat splash screen
        val splashCheck = getSharedPreferences("isSplash", MODE_PRIVATE).getBoolean("splashCheck", true)
        if(splashCheck == true){
            startActivity(Intent(this@MainActivity, SplashScreen::class.java))
            finish()
        }
            getSharedPreferences("isSplash", MODE_PRIVATE).edit()
                .putBoolean("splashCheck", false).commit()
        }
//      bundle
        fun getBundle() {
            mBundle = intent.getBundleExtra("register")!!
            vUsername = mBundle.getString("username")!!
            vPassword = mBundle.getString("password")!!

            binding.inputUsername.editText?.setText(vUsername)
            binding.inputPassword.editText?.setText(vPassword)
        }
    }