package com.example.ugd3_c_10898.KritikSaran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_c_10898.R
import com.example.ugd3_c_10898.api.TubesApi
import com.example.ugd3_c_10898.models.KritikSaran
import com.example.ugd3_c_10898.models.User
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class TambahEditKritkSaran : AppCompatActivity() {

    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null
    private var judulKritik: EditText? = null
    private var kontenKritik: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_edit_kritk_saran)

        judulKritik = findViewById(R.id.inputJudul)
        kontenKritik = findViewById(R.id.inputKonten)
        layoutLoading = findViewById(R.id.layout_loading)
        queue = Volley.newRequestQueue(this)
        val btnCancel = findViewById<Button>(R.id.btnCancel)

        btnCancel.setOnClickListener{
            finish()
        }

        val btnSave = findViewById<Button>(R.id.btnSave)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val id = intent.getIntExtra("id", -1)
        if(id== -1){
            tvTitle.setText("Tambah Kritik Saran")
            btnSave.setOnClickListener { createKritikSaran() }
        }else{
            tvTitle.setText("Edit Kritik Saran")
            getKritikSaranById(id)

            btnSave.setOnClickListener { updateKritikSaran(id) }
        }
    }

    private fun getKritikSaranById(id: Int){
        setLoading(true)

        val stringRequest: StringRequest = object :
            StringRequest(
                Method.GET, TubesApi.getByIDKritikSaran + id,
                { response ->
                    val gson = Gson()
                    val jsonObject = JSONObject(response)
                    var kritikSaran = gson.fromJson(jsonObject.getJSONObject("data").toString(), KritikSaran::class.java)
                    judulKritik!!.setText(kritikSaran.judul)
                    kontenKritik!!.setText(kritikSaran.content)
                    Toast.makeText(this@TambahEditKritkSaran,JSONObject(response).getString("message"), Toast.LENGTH_SHORT).show()
                    setLoading(false)
                },
                Response.ErrorListener{ error ->
                    setLoading(false)
                    try{
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception){
                        Toast.makeText(this@TambahEditKritkSaran, e.message, Toast.LENGTH_SHORT).show()
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
    private fun createKritikSaran(){
        setLoading(true)
        val kritikSaran = KritikSaran(
            judulKritik!!.text.toString(),
            kontenKritik!!.text.toString(),
        )
        val stringRequest: StringRequest =
            object: StringRequest(Method.POST, TubesApi.createKritikSaran, Response.Listener { response ->
                val gson = Gson()
                var mahasiswa = gson.fromJson(response, KritikSaran::class.java)

                if(mahasiswa != null)
                    Toast.makeText(this@TambahEditKritkSaran, JSONObject(response).getString("message"), Toast.LENGTH_SHORT).show()


                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
                    Toast.makeText(this@TambahEditKritkSaran, e.message, Toast.LENGTH_SHORT).show()
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
                    val requestBody = gson.toJson(kritikSaran)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }

        queue!!.add(stringRequest)
    }

    private fun updateKritikSaran(id: Int){
        setLoading(true)
        val kritikSaran = KritikSaran(
            judulKritik!!.text.toString(),
            kontenKritik!!.text.toString(),
        )
        val stringRequest: StringRequest =
            object: StringRequest(Method.PUT, TubesApi.updateKritikSaran + id, Response.Listener { response ->
                val gson = Gson()
                var mahasiswa = gson.fromJson(response, KritikSaran::class.java)

                if(mahasiswa != null)
                    Toast.makeText(this@TambahEditKritkSaran, JSONObject(response).getString("message"), Toast.LENGTH_SHORT).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
                    Toast.makeText(this@TambahEditKritkSaran, e.message, Toast.LENGTH_SHORT).show()
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
                    val requestBody = gson.toJson(kritikSaran)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
        queue!!.add(stringRequest)
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