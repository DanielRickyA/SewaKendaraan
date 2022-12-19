package com.example.ugd3_c_10898.Profil

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_c_10898.*
import com.example.ugd3_c_10898.KritikSaran.KritikSaranActivity
import com.example.ugd3_c_10898.api.TubesApi
import com.example.ugd3_c_10898.databinding.FragmentProfileBinding
import com.example.ugd3_c_10898.models.User
import com.example.ugd3_c_10898.room.user.UserDB
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ProfileFragment : Fragment() {
    val db by lazy { UserDB(this.requireActivity()) }
    var pref: SharedPreferences? = null
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentProfileBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        queue = Volley.newRequestQueue(requireActivity())
        //ini bingung bang
        val id = pref!!.getInt("id", -1)
        getUserById(id)

//        val user = db.userDao().getUser(pref!!.getInt("id",0))
//
//        binding.UsernameProfil.setText(user.username)
//        binding.EmailProfil.setText(user.email)
//        binding.TanggalProfil.setText(user.tglLahir)
//        binding.NomorTelepon.setText(user.noHp)



        val btn: Button = view.findViewById(R.id.EditButton)
        btn.setOnClickListener {
            (activity as HomeActivity).changeFragment(EditProfilFragment())
        }

        binding.btnExit.setOnClickListener{
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin keluar?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Logout"){_,_ ->
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }.show()

        }

        binding.btnProfil.setOnClickListener{
            (activity as HomeActivity).changeFragment(CameraFragment())
        }
        binding.fabAdd.setOnClickListener{
            val intent = Intent(activity, KritikSaranActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getUserById(id: Int) {

//        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, TubesApi.getUserId + id, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                var user = gson.fromJson(jsonObject.getJSONObject("data").toString(), User::class.java)
                println(user.username)
                println(user.tglLahir)
                println(user.noHp)

                binding.UsernameProfil.setText(user.username)
                binding.EmailProfil.setText(user.email)
                binding.TanggalProfil.setText(user.tglLahir)
                binding.NomorTelepon.setText(user.noHp)
                
            },Response.ErrorListener { error ->
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


}