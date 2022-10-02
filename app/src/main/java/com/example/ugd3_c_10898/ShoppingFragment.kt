package com.example.ugd3_c_10898

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.ugd3_c_10898.room.mobil.SewaMobil
import com.example.ugd3_c_10898.room.user.UserDB
import com.example.ugd3_c_10898.databinding.FragmentShoppingBinding


class ShoppingFragment : Fragment() {

    // Code Room untuk Users
    val db by lazy { UserDB(this.requireActivity()) }
    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val noticationId1 = 101
    private val noticationId2 = 102


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return ShoppingFragmentBinding.inflate(R.layout.fragment_shopping, container, false)
        _binding = FragmentShoppingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputLokasi : TextView =  view.findViewById(R.id.inputLokasi)
        val inputTanggalPinjam : TextView = view.findViewById(R.id.inputTanggalPinjam)
        val inputTanggalKembali : TextView = view.findViewById(R.id.inputTanggalKembali)
        val inputModelKendaraan : TextView = view.findViewById(R.id.inputModelKendaraan)
        val btn: Button = view.findViewById(R.id.btnTambah)
        val cek: Button = view.findViewById(R.id.CekPesanan)
        btn.setOnClickListener {
            if (!inputLokasi.text.isEmpty() && !inputTanggalPinjam.text.isEmpty() && !inputTanggalKembali.text.isEmpty() && !inputModelKendaraan.text.isEmpty())
                db.SewaMobilDao().addSewaMobil(
                    SewaMobil(0,inputLokasi.text.toString(), inputTanggalPinjam.text.toString(), inputTanggalKembali.text.toString(), inputModelKendaraan.text.toString())
                )
            createNotificationChanel()
            sendNotification()
            (activity as HomeActivity).changeFragment(rv_show_pemesanan())
        }
        cek.setOnClickListener{
            (activity as HomeActivity).changeFragment(rv_show_pemesanan())
        }

    }

    private fun createNotificationChanel(){
        val name = "Notification Title"
        val descriptionText = "Notification Description"

        val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = descriptionText
        }
        val channel2 = NotificationChannel(CHANNEL_ID_2, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = descriptionText
        }

        val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel1)
        notificationManager.createNotificationChannel(channel2)
    }

    private fun sendNotification() {
        val SUMMARY_ID = 0
        val GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL"

        val newMessageNotification1 = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_mail_24)
            .setContentTitle("Jasa Cycle Fast")
            .setContentText("Selamat Anda Berhasil Melakukan Pemesanan")
            .setGroup(GROUP_KEY_WORK_EMAIL)
            .build()

        val newMessageNotification2 = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_mail_24)
            .setContentTitle("Deni Sumargo")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        "Untuk Pemesanan ini segera menyertakan identitas lewat nomor 084443766577 ya agar segera diverivikasi"
                    )
            )
            .setGroup(GROUP_KEY_WORK_EMAIL)
            .build()
        val summaryNotification = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID_1)
            .setContentTitle("Pesan Penting")
            //set content text to support devices running API level < 24
            .setContentText("Two new messages")
            .setSmallIcon(R.drawable.ic_mail_24)
            //build summary info into InboxStyle template
            .setStyle(NotificationCompat.InboxStyle()
                .setBigContentTitle("2 new messages")
                .setSummaryText("JasaFast@gmail.com"))
            //specify which group this notification belongs to
            .setGroup(GROUP_KEY_WORK_EMAIL)
            //set this notification as the summary for the group
            .setGroupSummary(true)
            .setColor(Color.RED)
            .build()

        NotificationManagerCompat.from(this.requireContext()).apply {
            notify(noticationId1, newMessageNotification1)
            notify(noticationId2, newMessageNotification2)
            notify(SUMMARY_ID, summaryNotification)
        }
    }
}