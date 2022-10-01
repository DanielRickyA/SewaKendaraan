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
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

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
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val noticationId1 = 101

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
            createNotificationChanel()
            sendNotification()

            startActivity(intent)
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
        val intent : Intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val broadcastIntent : Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", "Selamat Datang "+binding?.etUsername?.text.toString())

        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_message_24)
            .setContentTitle("Jasa Cycle ")
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