package com.example.ugd3_c_10898

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.ugd3_c_10898.databinding.FragmentScannerBinding

class ScannerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val noticationId1 = 101

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
//        return inflater.inflate(R.layout.fragment_mail, container, false)
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val btn: Button = view.findViewById(R.id.btnMessage)
//        btn.setOnClickListener {
//            createNotificationChanel()
//            sendNotification()
        }
    }

//    private fun createNotificationChanel(){
//        val name = "Notification Title"
//        val descriptionText = "Notification Description"
//
//        val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
//            description = descriptionText
//        }
//
//        val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel1)
//    }
//
//    private fun sendNotification(){
//        val intent = Intent(this.requireContext(), MailFragment::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this.requireContext(), 0, intent, 0)
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//
//        var message1 = NotificationCompat.MessagingStyle.Message(messages[0].getText(),
//            messages[0].getTime(),
//            messages[0].getSender())
//        var message2 = NotificationCompat.MessagingStyle.Message(messages[1].getText(),
//            messages[1].getTime(),
//            messages[1].getSender())
//        var notification = NotificationCompat.Builder(context, CHANNEL_ID)
//            .setSmallIcon(R.drawable.new_message)
//            .setStyle(NotificationCompat.MessagingStyle(resources.getString(R.string.reply_name))
//                .addMessage(message1)
//                .addMessage(message2))
//            .build()
//        val builder = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID_1)
//            .setSmallIcon(R.drawable.ic_message_24)
//            .setContentTitle("Jasa Cycle")
//            .setContentText("Selamat Anda Berhasil Melakukan Pemesanan")
//            .setColor(Color.BLUE)
//            .setAutoCancel(true)
//            .setOnlyAlertOnce(true)
//            .setSound(defaultSoundUri)
//            .setContentIntent(pendingIntent)
//            .setPriority(NotificationCompat.PRIORITY_LOW)
//
//        with(NotificationManagerCompat.from(this.requireContext())){
//            notify(noticationId1, builder.build())
//        }
//    }
//}