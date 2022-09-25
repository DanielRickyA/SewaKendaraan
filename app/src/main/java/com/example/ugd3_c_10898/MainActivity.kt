package com.example.ugd3_c_10898

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ugd3_c_10898.room.user.UserDB
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    private lateinit var inputUsername:TextInputLayout
    private lateinit var inputPassword:TextInputLayout
    private lateinit var mainLayout:ConstraintLayout
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

        inputUsername=findViewById(R.id.inputUsername)
        inputPassword=findViewById(R.id.inputPassword)
        mainLayout=findViewById(R.id.loginLayout)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnLogin:Button=findViewById(R.id.btnLogin)
        val btnClear:Button=findViewById(R.id.btnClear)

        if(intent.getBundleExtra("register")!=null){
            getBundle()
        }
        btnRegister.setOnClickListener{
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        btnClear.setOnClickListener{
            inputUsername.getEditText()?.setText("")
            inputPassword.getEditText()?.setText("")

            Snackbar.make(mainLayout, "Text Berhasil Dihapus",Snackbar.LENGTH_LONG).show()
        }

        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin=false
            val username:String=inputUsername.getEditText()?.getText().toString()
            val password:String=inputPassword.getEditText()?.getText().toString()

            if(username.isEmpty()){
                inputUsername.setError("Username must be filled with text")
                checkLogin=false
            }

            if(password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                checkLogin=false
            }
            val user = db.userDao().getUsersByUsername(username,password)
            if( user != null){
                checkLogin=true
                val edit : SharedPreferences.Editor = pref!!.edit()
                edit.putInt("id",user.id)
                edit.apply()
            }else if(intent.getBundleExtra("register")!=null){

                checkLogin=true
            }

            if(checkLogin){
                val moveHome=Intent(this@MainActivity,HomeActivity::class.java)
                startActivity(moveHome)
            }

        })

        val splashCheck = getSharedPreferences("isSplash", MODE_PRIVATE).getBoolean("splashCheck", true)

        if(splashCheck == true){
            startActivity(Intent(this@MainActivity, SplashScreen::class.java))
            finish()
        }
            getSharedPreferences("isSplash", MODE_PRIVATE).edit()
                .putBoolean("splashCheck", false).commit()
        }

        fun getBundle() {
            mBundle = intent.getBundleExtra("register")!!
            vUsername = mBundle.getString("username")!!
            vPassword = mBundle.getString("password")!!

            inputUsername.editText?.setText(vUsername)
            inputPassword.editText?.setText(vPassword)
        }
    }