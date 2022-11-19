package com.example.ugd3_c_10898

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.ugd3_c_10898.api.TubesApi
import com.example.ugd3_c_10898.databinding.ActivityMainBinding
import com.example.ugd3_c_10898.models.Login
import com.example.ugd3_c_10898.room.user.UserDB
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_kendaraan.view.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    private lateinit var binding: ActivityMainBinding
    lateinit var  mBundle: Bundle
    var pref: SharedPreferences? = null
    var vUsername : String = ""
    var vPassword : String = ""

    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

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
//        if(intent.getBundleExtra("register")!=null){
//            getBundle()
//        }


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

            val login = Login(
                username,
                password
            )

//            val user = db.userDao().getUsersByUsername(username,password)
            val user: StringRequest =
                object : StringRequest(Method.POST, TubesApi.login, Response.Listener { response ->
                    val gson = Gson()
                    var login = gson.fromJson(response, Login::class.java)

                    if (login != null)
                        Toast.makeText(this@MainActivity, "Register Berhasil", Toast.LENGTH_SHORT).show()

                    val returnIntent = Intent()
                    setResult(RESULT_OK, returnIntent)
                    finish()

                    setLoading(false)
                }, Response.ErrorListener { error ->
                    setLoading(false)
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
                        val requestBody = gson.toJson(login)
                        return requestBody.toByteArray(StandardCharsets.UTF_8)
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }
                }

            queue!!.add(user)

            if( user != null ){
                checkLogin=true
//                val edit : SharedPreferences.Editor = pref!!.edit()
//                edit.putInt("id",user.id)
//                edit.apply()
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
//        fun getBundle() {
//            mBundle = intent.getBundleExtra("register")!!
//            vUsername = mBundle.getString("username")!!
//            vPassword = mBundle.getString("password")!!
//
//            binding.inputUsername.editText?.setText(vUsername)
//            binding.inputPassword.editText?.setText(vPassword)
//        }
        private fun setLoading(isLoading: Boolean) {
            if (isLoading) {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                layoutLoading!!.visibility = View.VISIBLE
            } else {
                window.clearFlags (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                layoutLoading!!.visibility = View.INVISIBLE
            }
        }
    }