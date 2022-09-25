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
import com.example.ugd3_c_10898.room.user.User
import com.example.ugd3_c_10898.room.user.UserDB
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_edit_profil.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_edit_profil : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val db by lazy { UserDB(this.requireActivity()) }
    var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        val btn: Button = view.findViewById(R.id.btnSave)
        val btnBack: Button = view.findViewById(R.id.btnBack)

        val userEdit: TextInputEditText = view.findViewById(R.id.userEdit)
        val emailEdit: TextInputEditText = view.findViewById(R.id.emailEdit)
        val tanggalEdit: TextInputEditText = view.findViewById(R.id.tanggalEdit)
        val nomorEdit: TextInputEditText = view.findViewById(R.id.nomrEdit)

        val user = db.userDao().getUser(pref!!.getInt("id",0))
        val password = db.userDao().getUser(pref!!.getInt("id",0)).password
        userEdit.setText(user.username)
        emailEdit.setText(user.email)
        tanggalEdit.setText(user.tglLahir)
        nomorEdit.setText(user.noHp.toString())
        btn.setOnClickListener {
            db.userDao().updateUser(User(pref!!.getInt("id",0),userEdit.text.toString(),password, emailEdit.text.toString(),tanggalEdit.text.toString(),nomorEdit.text.toString().toInt()))
            (activity as HomeActivity).changeFragment(ProfileFragment())
        }

        btnBack.setOnClickListener {
            (activity as HomeActivity).changeFragment(ProfileFragment())
        }
    }
}