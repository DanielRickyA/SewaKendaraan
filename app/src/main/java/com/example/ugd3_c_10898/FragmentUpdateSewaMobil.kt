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
import com.example.ugd3_c_10898.room.mobil.SewaMobil
import com.example.ugd3_c_10898.room.mobil.SewaMobilDB
import com.example.ugd3_c_10898.room.user.User
import com.example.ugd3_c_10898.room.user.UserDB

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentUpdateSewaMobil.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentUpdateSewaMobil : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val db by lazy { SewaMobilDB(this.requireActivity()) }
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
        return inflater.inflate(R.layout.fragment_update_sewa_mobil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        val btn: Button = view.findViewById(R.id.btnUpdateSewa)
        val btnBack: Button = view.findViewById(R.id.btnBack)
        val btnDelete : Button = view.findViewById(R.id.btnDeleteSewa)

        val lokasiEdit : TextView =  view.findViewById(R.id.inputLokasi)
        val tanggalPinjamEdit : TextView = view.findViewById(R.id.inputTanggalPinjam)
        val tanggalKembaliEdit : TextView = view.findViewById(R.id.inputTanggalKembali)
        val modelKendaraanEdit : TextView = view.findViewById(R.id.inputModelKendaraan)

        val sewaMobil = db.SewaMobilDao().getDataSewaMobil(pref!!.getInt("id",0))

        lokasiEdit.setText(sewaMobil.lokasi)
        tanggalPinjamEdit.setText(sewaMobil.tanggalPinjam)
        tanggalKembaliEdit.setText(sewaMobil.tanggalKembali)
        modelKendaraanEdit.setText(sewaMobil.modelKendaraan)

        btn.setOnClickListener {
            db.SewaMobilDao().updateSewaMobil(SewaMobil(pref!!.getInt("id",0),lokasiEdit.text.toString(),tanggalPinjamEdit.text.toString(),tanggalKembaliEdit.text.toString(),modelKendaraanEdit.text.toString()))
            (activity as HomeActivity).changeFragment(rv_show_pemesanan())
        }

        btnBack.setOnClickListener {
            (activity as HomeActivity).changeFragment(rv_show_pemesanan())
        }

        btnDelete.setOnClickListener {
            db.SewaMobilDao().deleteSewaMobil(SewaMobil(pref!!.getInt("id",0),lokasiEdit.text.toString(),tanggalPinjamEdit.text.toString(),tanggalKembaliEdit.text.toString(),modelKendaraanEdit.text.toString()))
            (activity as HomeActivity).changeFragment(rv_show_pemesanan())
        }
    }
}