package com.example.ugd3_c_10898

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isEmpty
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_c_10898.api.TubesApi
import com.example.ugd3_c_10898.databinding.ActivityMainBinding
import com.example.ugd3_c_10898.models.Login
import com.example.ugd3_c_10898.models.User
import com.example.ugd3_c_10898.room.user.UserDB
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_kendaraan.view.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    private lateinit var binding: ActivityMainBinding
    lateinit var  mBundle: Bundle
    private lateinit var user: User
    var pref: SharedPreferences? = null
    var vUsername : String = ""
    var vPassword : String = ""

    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.hide()

        queue = Volley.newRequestQueue(this)
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
        binding.btnLogin.setOnClickListener {
            var checkLogin=false


            if( !binding.inputUsername.isEmpty() && !binding.inputPassword.isEmpty()){
                checkLogin=true

            }else{
                binding.inputUsername.setError("Username Salah")
                binding.inputPassword.setError("Password Salah")
            }

            if(checkLogin){
                login()
            }else{
                binding.inputUsername.setError("Username Salah")
                binding.inputPassword.setError("Password Salah")
            }

        }
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

//      Fungsi Login
        private fun login() {
            val username:String=binding.inputUsername.getEditText()?.getText().toString()
            val password:String=binding.inputPassword.getEditText()?.getText().toString()

            val sewa = Login(
                username,
                password
            )

            val user: StringRequest =
                object : StringRequest(Method.POST, TubesApi.login, Response.Listener { response ->
                    val gson = Gson()
                    var login = gson.fromJson(response, Login::class.java)
                    val jsonObject = JSONObject(response)
                    if (login != null)
                        Toast.makeText(this@MainActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    val edit : SharedPreferences.Editor = pref!!.edit()
                    edit.putInt("id", jsonObject.getJSONObject("user").getInt("id"))
                    edit.apply()
                    val moveHome=Intent(this@MainActivity,HomeActivity::class.java)
                    startActivity(moveHome)
                    finish()
                }, Response.ErrorListener { error ->
                    try {
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@MainActivity,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Accept"] = "application/json"
                        return headers
                    }

                    @Throws(AuthFailureError::class)
                    override fun getBody(): ByteArray {
                        val gson = Gson()
                        val requestBody = gson.toJson(sewa)
                        return requestBody.toByteArray(StandardCharsets.UTF_8)
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }
                }
            queue!!.add(user)
        }
    }