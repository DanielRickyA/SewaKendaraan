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
    private val noticationId1 = 101


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

        val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel1)
    }

    private fun sendNotification(){
        val intent = Intent(this.requireContext(), ShoppingFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this.requireContext(), 0, intent, 0)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_shopping_cart_24)
            .setContentTitle("Jasa Cycle")
            .setContentText("Selamat Anda Berhasil Melakukan Pemesanan")
            .setColor(Color.BLUE)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        with(NotificationManagerCompat.from(this.requireContext())){
            notify(noticationId1, builder.build())
        }
    }
}