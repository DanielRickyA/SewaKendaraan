package com.example.ugd3_c_10898

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.ugd3_c_10898.room.user.UserDB
import kotlinx.android.synthetic.*
import org.w3c.dom.Text

class ProfileFragment : Fragment() {
    val db by lazy { UserDB(this.requireActivity()) }
    var pref: SharedPreferences? = null
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        val username :TextView =  view.findViewById(R.id.UsernameProfil)
        val email : TextView = view.findViewById(R.id.EmailProfil)
        val tanggal : TextView = view.findViewById(R.id.TanggalProfil)
        val phone : TextView = view.findViewById(R.id.NomorTelepon)

        val user = db.userDao().getUser(pref!!.getInt("id",0))

        username.setText(user.username)
        email.setText(user.email)
        tanggal.setText(user.tglLahir)
        phone.setText(user.noHp.toString())

        val btn: Button = view.findViewById(R.id.EditButton)
        btn.setOnClickListener {
            (activity as HomeActivity).changeFragment(fragment_edit_profil())
        }
    }
}