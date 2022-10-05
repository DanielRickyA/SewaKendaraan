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
import com.example.ugd3_c_10898.databinding.FragmentEditProfilBinding
import com.example.ugd3_c_10898.room.user.User
import com.example.ugd3_c_10898.room.user.UserDB
import com.google.android.material.textfield.TextInputEditText

class EditProfilFragment : Fragment() {

    val db by lazy { UserDB(this.requireActivity()) }
    var pref: SharedPreferences? = null
    private var _binding: FragmentEditProfilBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentEditProfilBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)


        val user = db.userDao().getUser(pref!!.getInt("id",0))
        val password = db.userDao().getUser(pref!!.getInt("id",0)).password

        binding.userEdit.setText(user.username)
        binding.emailEdit.setText(user.email)
        binding.tanggalEdit.setText(user.tglLahir)
        binding.nomrEdit.setText(user.noHp)
        //btn Save
        binding.btnSave.setOnClickListener {
            db.userDao().updateUser(User(pref!!.getInt("id",0),binding.userEdit.text.toString(),password, binding.emailEdit.text.toString(),
                binding.tanggalEdit.text.toString(),binding.nomrEdit.text.toString()))
            (activity as HomeActivity).changeFragment(ProfileFragment())
        }
        //btn Back
        binding.btnBack.setOnClickListener {
            (activity as HomeActivity).changeFragment(ProfileFragment())
        }
    }
}