package com.example.ugd3_c_10898

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_c_10898.api.TubesApi

import com.example.ugd3_c_10898.models.SewaKendaraan
import com.example.ugd3_c_10898.room.mobil.SewaMobil
import com.example.ugd3_c_10898.room.mobil.SewaMobilDao
import com.example.ugd3_c_10898.room.user.UserDB
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class RVShowPemesanan : Fragment() {
    lateinit var rvPemesanan: RecyclerView
    private var queue: RequestQueue? = null
     var pemesananAdapter: RVPemesananAdapter = RVPemesananAdapter(arrayListOf(), object: RVPemesananAdapter.OnAdapterListener{
        override fun onUpdate(sewaKendaraan: SewaKendaraan) {
            val fragment = UpdateSewaMobilFragment()
            val bundle = Bundle()
            bundle.putInt("id", sewaKendaraan.id!!.toInt())
            fragment.arguments = bundle
            (activity as HomeActivity).changeFragment(fragment)
        }

    })


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
        return inflater.inflate(R.layout.fragment_rv_show_pemesanan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queue = Volley.newRequestQueue(requireContext())

        val db by lazy {UserDB(activity as HomeActivity)}

        rvPemesanan = view.findViewById(R.id.rvPemesanan)

        val btnBack: Button = view.findViewById(R.id.btnBack)


        setUpRecycleView()
        loadData()
        btnBack.setOnClickListener {
            (activity as HomeActivity).changeFragment(ShoppingFragment())
        }
    }

    fun setUpRecycleView(){
        rvPemesanan.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pemesananAdapter
        }
    }
    fun loadData(){
        getAllSewaKendaraan()
    }

    private fun getAllSewaKendaraan(){
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, TubesApi.getAllSewa, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                var sewa : Array<SewaKendaraan> = gson.fromJson(jsonObject.getJSONArray("data").toString(), Array<SewaKendaraan>::class.java)
                pemesananAdapter.setData(sewa.toList())
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
}