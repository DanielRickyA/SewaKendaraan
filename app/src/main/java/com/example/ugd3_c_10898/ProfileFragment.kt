package com.example.ugd3_c_10898

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.ugd3_c_10898.databinding.FragmentProfileBinding
import com.example.ugd3_c_10898.room.user.UserDB

class ProfileFragment : Fragment() {
    val db by lazy { UserDB(this.requireActivity()) }
    var pref: SharedPreferences? = null
    private var _binding: FragmentProfileBinding? = null
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
        _binding = FragmentProfileBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
//        val username :TextView =  view.findViewById(R.id.UsernameProfil)
//        val email : TextView = view.findViewById(R.id.EmailProfil)
//        val tanggal : TextView = view.findViewById(R.id.TanggalProfil)
//        val phone : TextView = view.findViewById(R.id.NomorTelepon)
//        val exit : Button = view.findViewById(R.id.btnExit)

        val user = db.userDao().getUser(pref!!.getInt("id",0))

        binding.UsernameProfil.setText(user.username)
        binding.EmailProfil.setText(user.email)
        binding.TanggalProfil.setText(user.tglLahir)
        binding.NomorTelepon.setText(user.noHp)

        val btn: Button = view.findViewById(R.id.EditButton)
        btn.setOnClickListener {
            (activity as HomeActivity).changeFragment(EditProfilFragment())
        }

        binding.btnExit.setOnClickListener{
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}