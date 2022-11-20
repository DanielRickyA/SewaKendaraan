package com.example.ugd3_c_10898

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_c_10898.api.TubesApi

import com.example.ugd3_c_10898.databinding.ActivityRegisterBinding

import com.google.android.material.textfield.TextInputEditText
import com.example.ugd3_c_10898.models.User
//import com.example.ugd3_c_10898.room.user.User
import com.example.ugd3_c_10898.room.user.UserDB
import com.example.ugd3_c_10898.room.Constant
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val noticationId1 = 101

    private var queue: RequestQueue? = null
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
//    private fun setupListener() {
//
//        db.userDao().addUser(
//            User(
//                0.toString(),binding.etUsername.text.toString(), binding.etPassword.text.toString(),  binding.etEmail.text.toString(),
//                binding.inputTL.text.toString(), (binding.etNumber.text.toString()))
//        )
//
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        getSupportActionBar()?.hide()

        queue = Volley.newRequestQueue(this)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle("Register Cycle Fast")

        binding.btnBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        binding.btnActionRegister.setOnClickListener{
            var bUsername  = binding.etUsername.text.toString()
            val bPassword = binding.etPassword.text.toString()
            val bEmail = binding.etEmail.text.toString()
            val bTanggal = binding.inputTL.text.toString()
            val bNumber = binding.etNumber.text.toString()
            var cekRegis = true
            if(bUsername.isEmpty()){
                etUsername.setError("Username Tidak Boleh Kosong")
                cekRegis = false
            }
            if(bPassword.isEmpty()){
                etPassword.setError("Password Tidak Boleh Kosong")
                cekRegis = false
            }
            if(bEmail.isEmpty()){
                etEmail.setError("Email Tidak Boleh Kosong")
                cekRegis = false
            }
            if(bTanggal.isEmpty()){
                inputTL.setError("Tanggal Tidak Boleh Kosong")
                cekRegis = false
            }
            if(bNumber.isEmpty()){
                etNumber.setError("Nomor Telefon Tidak Boleh Kosong")
                cekRegis = false
            }

            if(cekRegis) {
//                setupListener()
                val intent = Intent(this, MainActivity::class.java)
//                val mBundle = Bundle()
//                mBundle.putString("username",bUsername)
//                mBundle.putString("password",bPassword)
//                intent.putExtra("register", mBundle)
                val register = User(
                    bUsername,
                    bPassword,
                    bEmail,
                    bTanggal,
                    bNumber,
                )

                val stringRequest: StringRequest =
                    object : StringRequest(
                        Method.POST,
                        TubesApi.register,
                        Response.Listener { response ->
                            val gson = Gson()
                            var register = gson.fromJson(response, User::class.java)

                            if (register != null) {
                                createNotificationChanel()
                                sendNotification()
                            }

                            val returnIntent = Intent()
                            setResult(RESULT_OK, returnIntent)
                            finish()


                        },
                        Response.ErrorListener { error ->
                            try {
                                val responseBody =
                                    String(error.networkResponse.data, StandardCharsets.UTF_8)
                                val errors = JSONObject(responseBody)
                                Toast.makeText(
                                    this@RegisterActivity,
                                    errors.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } catch (e: Exception) {
                                Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_SHORT)
                                    .show()
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
                            val requestBody = gson.toJson(register)
                            return requestBody.toByteArray(StandardCharsets.UTF_8)
                        }

                        override fun getBodyContentType(): String {
                            return "application/json"
                        }
                    }

                queue!!.add(stringRequest)
                startActivity(intent)
            }
        }
    }

    private fun createNotificationChanel(){
        val name = "Notification Title"
        val descriptionText = "Notifivation Description"

        val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel1)
    }

    private fun sendNotification(){

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val broadcastIntent : Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", "Selamat Datang "+binding?.etUsername?.text.toString())

        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_message_24)
            .setContentTitle("Jasa Cycle")
            .setContentText("Halo "+ binding?.etUsername?.text.toString() +" Anda Berhasil Melakukan Register")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.RED)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.big_picture)
                    )
            )
            .addAction(R.mipmap.ic_launcher, "Press To See Surprise", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)



        with(NotificationManagerCompat.from(this)){
            notify(noticationId1, builder.build())
        }
    }


}