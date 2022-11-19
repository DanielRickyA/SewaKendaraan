package com.example.ugd3_c_10898

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_c_10898.api.TubesApi
import com.example.ugd3_c_10898.databinding.FragmentUpdateSewaMobilBinding
import com.example.ugd3_c_10898.models.SewaKendaraan
import com.example.ugd3_c_10898.room.mobil.SewaMobil
//import com.example.ugd3_c_10898.room.User.Use
import com.example.ugd3_c_10898.room.user.UserDB
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_update_sewa_mobil.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class UpdateSewaMobilFragment : Fragment() {

    val db by lazy { UserDB(this.requireActivity()) }
    var pref: SharedPreferences? = null

    private var _binding: FragmentUpdateSewaMobilBinding? = null
    private val binding get() = _binding!!
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val CHANNEL_ID_3 = "channel_notification_03"
    private val noticationId1 = 101
    private val noticationId2 = 102
    private val noticationId3 = 103
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_update_sewa_mobil, container, false)
        _binding = FragmentUpdateSewaMobilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queue = Volley.newRequestQueue(requireContext())
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)

        val id: Int = requireArguments().getInt("id")


        getById(id)

        createNotificationChanel()

        binding.btnUpdateSewa.setOnClickListener {
            if(binding.inputLokasi.text.toString().isEmpty() || binding.inputTanggalPinjam.text.toString().isEmpty() ||
                inputTanggalKembali.text.toString().isEmpty() || inputModelKendaraan.text.toString().isEmpty()){
                if (binding.inputLokasi.text.toString().isEmpty()){
                    inputLokasi.setError("Data Tidak Boleh Kosong")
                }
                if (binding.inputTanggalPinjam.text.toString().isEmpty()){
                    inputTanggalPinjam.setError("Data Tidak Boleh Kosong")
                }
                if (binding.inputTanggalKembali.text.toString().isEmpty()){
                    inputTanggalKembali.setError("Data Tidak Boleh Kosong")
                }
                if (binding.inputModelKendaraan.text.toString().isEmpty()){
                    inputModelKendaraan.setError("Data Tidak Boleh Kosong")
                }
            }else{
                if (!binding.inputLokasi.text.toString().isEmpty() || !binding.inputTanggalPinjam.text.toString().isEmpty() ||
                    !binding.inputTanggalKembali.text.toString().isEmpty() || !binding.inputModelKendaraan.text.toString().isEmpty()){
                    UpdateSewaKendaraan(id)
                }
            }

        }

        binding.btnBack.setOnClickListener {
            (activity as HomeActivity).changeFragment(RVShowPemesanan())
        }

        binding.btnDeleteSewa.setOnClickListener {
            deleteSewa(id)


        }
    }
//    Get By Id
    private fun getById(id: Int){
    val stringRequest: StringRequest = object :
        StringRequest(Method.GET, TubesApi.getByIdSewa + id, Response.Listener { response ->
            val gson = Gson()
            val jsonObject = JSONObject(response)
            var sewa = gson.fromJson(jsonObject.getJSONObject("data").toString(), SewaKendaraan::class.java)

            binding.inputLokasi.setText(sewa.lokasi)
            binding.inputTanggalPinjam.setText(sewa.tanggalPinjam)
            binding.inputTanggalKembali.setText(sewa.tanggalKembali)
            binding.inputModelKendaraan.setText(sewa.modelKendaraan)
        }, Response.ErrorListener { error ->
            try {
                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                val errors = JSONObject(responseBody)
                Toast.makeText(requireActivity(), errors.getString("message"), Toast.LENGTH_SHORT).show()
            } catch (e: Exception){
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
            }
        }) {
        @Throws(AuthFailureError::class)
        override fun getHeaders(): Map<String, String> {
            val headers = HashMap<String, String>()
            headers["Accept"] = "application/json"
            return headers
        }

    }
    queue!!.add(stringRequest)
}

//    Update Sewa Kendaraan
    private fun UpdateSewaKendaraan(id : Int){
    val sewa = SewaKendaraan(
        binding.inputLokasi.text.toString(), binding.inputTanggalPinjam.text.toString(),
        binding.inputTanggalKembali.text.toString(), binding.inputModelKendaraan.text.toString()
    )

    val stringRequest : StringRequest = object :
        StringRequest(Method.PUT, TubesApi.updateSewa + id, Response.Listener { response ->
            val gson = Gson()

            var sewa = gson.fromJson(response, SewaKendaraan::class.java)

            if(sewa != null)
                Toast.makeText(requireActivity(), "Data Berhasil diUpdate", Toast.LENGTH_SHORT).show()

            sendNotification2();
            sendNotification3();
            (activity as HomeActivity).changeFragment(RVShowPemesanan())
        }, Response.ErrorListener { error ->
            try {
//                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
//                val errors = JSONObject(responseBody)
                Toast.makeText(
                    requireContext(),
                    error.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
            }
        }){
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
    queue!!.add(stringRequest)
    }

    private fun deleteSewa(id : Int){
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, TubesApi.deleteSewa + id, Response.Listener { response ->
                val gson = Gson()
                var sewa = gson.fromJson(response, SewaKendaraan::class.java)

                (activity as HomeActivity).changeFragment(RVShowPemesanan())
                if(sewa!=null)
                    Toast.makeText(requireActivity(), "Data Berhasil diHapus", Toast.LENGTH_SHORT).show()

                sendNotification1()

            }, Response.ErrorListener { error ->
                try {
//                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
//                val errors = JSONObject(responseBody)
                    Toast.makeText(
                        requireContext(),
                        error.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

        }
        queue!!.add(stringRequest)
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

        val channel3 = NotificationChannel(CHANNEL_ID_3, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = descriptionText
        }

        val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel1)
        notificationManager.createNotificationChannel(channel2)
        notificationManager.createNotificationChannel(channel3)
    }

    private fun sendNotification1(){
        val intent = Intent(this.requireContext(), UpdateSewaMobilFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this.requireContext(), 0, intent, 0)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
            .setContentTitle("Jasa Cycle")
            .setContentText("Berhasil Menghapus Data Pemesanan")
            .setColor(Color.RED)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        with(NotificationManagerCompat.from(this.requireContext())){
            notify(noticationId1, builder.build())
        }
    }

    private fun sendNotification3(){
        val intent : Intent = Intent(this.requireContext(), UpdateSewaMobilFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this.requireContext(), 1, intent, 0)

        val builder = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID_3)
            .setSmallIcon(R.drawable.ic_message_24)
            .setContentTitle("Jasa Cycle")
//            .setContentText()
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.GREEN)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        "Halo Cyclers terima kasih sudah memperbaharui data pemesanan dalam menyewa Jasa Cycle kami yang dimana "+
                        "data pemesanan yang terbaru berupa lokasi ("+binding?.inputLokasi?.text.toString()+"), model kendaraan ("+binding?.inputModelKendaraan?.text.toString()
                                +"), tanggal pinjam ("+binding?.inputTanggalPinjam?.text.toString()+") dan tanggal kembali ("+binding?.inputTanggalKembali?.text.toString()+"). "
                                +"Jasa Cycle - Aman, Cepat, Praktis dan Terpercaya"
                    )

            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this.requireContext())){
            notify(noticationId3, builder.build())
        }
    }

    private fun sendNotification2(){
        val intent : Intent = Intent(this.requireContext(), UpdateSewaMobilFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this.requireContext(), 0, intent, 0)
        val broadcastIntent : Intent = Intent(this.requireContext(), NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", "Berhasil Mengedit Data Pemesanan")

        val actionIntent = PendingIntent.getBroadcast(this.requireContext(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID_2)
            .setSmallIcon(R.drawable.ic_message_24)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.GREEN)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("Lokasi : "+binding?.inputLokasi?.text.toString())
                    .addLine("Model Kendaraan : "+binding?.inputModelKendaraan?.text.toString())
                    .addLine("Tanggal Pinjam : "+binding?.inputTanggalPinjam?.text.toString())
                    .addLine("Tanggal Kembali : "+binding?.inputTanggalKembali?.text.toString())
                    .setBigContentTitle("Jasa Cycle")
            )
            .addAction(R.mipmap.ic_launcher, "lihat Selengkap nya", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this.requireContext())){
            notify(noticationId2, builder.build())
        }
    }
}