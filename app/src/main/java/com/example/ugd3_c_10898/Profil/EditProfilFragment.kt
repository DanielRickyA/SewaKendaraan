package com.example.ugd3_c_10898.Profil

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_c_10898.HomeActivity
import com.example.ugd3_c_10898.api.TubesApi
import com.example.ugd3_c_10898.databinding.FragmentEditProfilBinding
import com.example.ugd3_c_10898.models.EditUser
import com.example.ugd3_c_10898.room.user.UserDB
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_edit_profil.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class EditProfilFragment : Fragment() {

    val db by lazy { UserDB(this.requireActivity()) }
    var pref: SharedPreferences? = null
    private var _binding: FragmentEditProfilBinding? = null
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
        _binding = FragmentEditProfilBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        queue = Volley.newRequestQueue(requireActivity())

        val id = pref!!.getInt("id", -1)
        getUserById(id)

//        val user = db.userDao().getUser(pref!!.getInt("id",0))
//        val password = db.userDao().getUser(pref!!.getInt("id",0)).password

//        binding.userEdit.setText(user.username)
//        binding.emailEdit.setText(user.email)
//        binding.tanggalEdit.setText(user.tglLahir)
//        binding.nomrEdit.setText(user.noHp)
        //btn Save
        binding.btnSave.setOnClickListener {
            if(binding.userEdit.text.toString().isEmpty() || binding.emailEdit.text.toString().isEmpty() ||
                binding.tanggalEdit.text.toString().isEmpty() || binding.nomrEdit.text.toString().isEmpty()){
                if (binding.userEdit.text.toString().isEmpty()){
                    userEdit.setError("Data Tidak Boleh Kosong")
                }
                if (binding.emailEdit.text.toString().isEmpty()){
                    emailEdit.setError("Data Tidak Boleh Kosong")
                }
                if (binding.tanggalEdit.text.toString().isEmpty()){
                    tanggalEdit.setError("Data Tidak Boleh Kosong")
                }
                if (binding.nomrEdit.text.toString().isEmpty()){
                    nomrEdit.setError("Data Tidak Boleh Kosong")
                }
            }else{
                if (!binding.userEdit.text.toString().isEmpty() || !binding.emailEdit.text.toString().isEmpty() ||
                    !binding.tanggalEdit.text.toString().isEmpty() || !binding.tanggalEdit.text.toString().isEmpty()){
                    updateUser(id)
                }
            }
        }
        //btn Back
        binding.btnBack.setOnClickListener {
            (activity as HomeActivity).changeFragment(ProfileFragment())
        }
    }

    private fun getUserById(id: Int) {

        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, TubesApi.getUserId + id, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                var user = gson.fromJson(jsonObject.getJSONObject("data").toString(), com.example.ugd3_c_10898.models.User::class.java)
                println(user.username)
                binding.userEdit.setText(user.username)
                binding.emailEdit.setText(user.email)
                binding.tanggalEdit.setText(user.tglLahir)
                binding.nomrEdit.setText(user.noHp)

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

    private fun updateUser(id: Int) {

        val user = EditUser(
            binding.userEdit.text.toString(), binding.emailEdit.text.toString(),
            binding.tanggalEdit.text.toString(), binding.nomrEdit.text.toString()
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, TubesApi.updateUser + id, Response.Listener { response ->
                val gson = Gson()

                var user = gson.fromJson(response, EditUser::class.java)

                if(user != null)
                    Toast.makeText(requireActivity(), "Data Berhasil diUpdate", Toast.LENGTH_SHORT).show()

                (activity as HomeActivity).changeFragment(ProfileFragment())
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
                val requestBody = gson.toJson(user)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }
}