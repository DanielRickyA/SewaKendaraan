package com.example.ugd3_c_10898

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.ugd3_c_10898.room.mobil.SewaMobil
import com.example.ugd3_c_10898.room.user.UserDB


class ShoppingFragment : Fragment() {

    // Code Room untuk Users
    val db by lazy { UserDB(this.requireActivity()) }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputLokasi : TextView =  view.findViewById(R.id.inputLokasi)
        val inputTanggalPinjam : TextView = view.findViewById(R.id.inputTanggalPinjam)
        val inputTanggalKembali : TextView = view.findViewById(R.id.inputTanggalKembali)
        val inputModelKendaraan : TextView = view.findViewById(R.id.inputModelKendaraan)
        val btn: Button = view.findViewById(R.id.btnTambah)
        val cek: Button = view.findViewById(R.id.CekPesanan)
        btn.setOnClickListener {
            if (!inputLokasi.text.isEmpty() && !inputTanggalPinjam.text.isEmpty() && !inputTanggalKembali.text.isEmpty() && !inputModelKendaraan.text.isEmpty())
                db.SewaMobilDao().addSewaMobil(
                    SewaMobil(0,inputLokasi.text.toString(), inputTanggalPinjam.text.toString(), inputTanggalKembali.text.toString(), inputModelKendaraan.text.toString())
                )
            (activity as HomeActivity).changeFragment(rv_show_pemesanan())
        }
        cek.setOnClickListener{
            (activity as HomeActivity).changeFragment(rv_show_pemesanan())
        }

    }
}