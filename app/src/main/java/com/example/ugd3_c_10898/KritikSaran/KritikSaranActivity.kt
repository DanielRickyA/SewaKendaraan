package com.example.ugd3_c_10898.KritikSaran

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_c_10898.R
import com.example.ugd3_c_10898.api.TubesApi
import com.example.ugd3_c_10898.models.KritikSaran
import com.example.ugd3_c_10898.models.SewaMobil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_kritik_saran.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class KritikSaranActivity : AppCompatActivity() {
    private var srKritikSaran: SwipeRefreshLayout? = null
    private var adapter: KritikSaranAdapter? = null
    private var svKritikSaran: SearchView? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kritik_saran)

        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srKritikSaran = findViewById(R.id.sr_kritikSaran)
        svKritikSaran = findViewById(R.id.sv_kritikSaran)

        srKritikSaran?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { getAllKritikSaran() })
        svKritikSaran?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter!!.filter.filter(p0)
                return false
            }
        })
        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        fabAdd.setOnClickListener {
            val i = Intent(this@KritikSaranActivity,  TambahEditKritkSaran::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }

        val rvKritik = findViewById<RecyclerView>(R.id.rv_kritikSaran)
        adapter = KritikSaranAdapter(ArrayList(), this)
        rvKritik.layoutManager = LinearLayoutManager(this)
        rvKritik.adapter = adapter
        getAllKritikSaran()
    }

    private fun getAllKritikSaran(){
        sr_kritikSaran!!.isRefreshing = true
        val stringRequest : StringRequest = object:
            StringRequest(Method.GET, TubesApi.getAllKritikSaran, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                var kritik : Array<KritikSaran> = gson.fromJson(jsonObject.getJSONArray("data").toString(), Array<KritikSaran>::class.java)

                adapter!!.setKritikSaranList(kritik)
                adapter!!.filter.filter(svKritikSaran!!.query)
                srKritikSaran!!.isRefreshing = false

                if(!kritik.isEmpty())
                    Toast.makeText(this@KritikSaranActivity, "Data berhasil diambil", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this@KritikSaranActivity, "Data Kosong!", Toast.LENGTH_SHORT).show()

            }, Response.ErrorListener { error ->
                srKritikSaran!!.isRefreshing = false
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(this@KritikSaranActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: Exception){
                    Toast.makeText(this@KritikSaranActivity, e.message, Toast.LENGTH_SHORT).show()
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
    fun deleteKritik(id: Int){
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, TubesApi.deleteKritikSaran+id,Response.Listener { response ->
                setLoading(false)

                val gson = Gson()
                var mahasiswa = gson.fromJson(response, KritikSaran::class.java)
                if(mahasiswa != null)
                    Toast.makeText(this@KritikSaranActivity, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()

                getAllKritikSaran()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(this@KritikSaranActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: java.lang.Exception){
                    Toast.makeText(this@KritikSaranActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = java.util.HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LAUNCH_ADD_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                getAllKritikSaran()
            }
        }
    }

    private fun setLoading(isLoading: Boolean){
        if(isLoading){
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.INVISIBLE
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.INVISIBLE
        }
    }
}