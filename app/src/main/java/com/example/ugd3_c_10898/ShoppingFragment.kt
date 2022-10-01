package com.example.ugd3_c_10898

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.content.Intent
import android.widget.TextView
import com.example.ugd3_c_10898.databinding.ActivityRegisterBinding
import com.example.ugd3_c_10898.room.Constant
import com.example.ugd3_c_10898.room.mobil.SewaMobil
import com.example.ugd3_c_10898.room.mobil.SewaMobilDB
import com.example.ugd3_c_10898.room.mobil.SewaMobilDao
import com.example.ugd3_c_10898.room.user.User
import com.example.ugd3_c_10898.room.user.UserDB
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_register.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShoppingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoppingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : btnTambahBinding

    // Code Room untuk Users
    val db by lazy { SewaMobilDB(this.requireActivity()) }
    private var sewaMobilId: Int = 0

//    fun setupView(){
//        Constant.TYPE_CREATE;{
//            btn.visibility = View.GONE
//        }
//    }

    private fun setupListener() {
        db.SewaMobilDao().addSewaMobil()(
            SewaMobil(0,binding.inputLokasi.text.toString(), binding.inputTanggalPinjam.text.toString(), binding.inputTanggalKembali.text.toString(), binding.inputModelKendaraan.text.toString())
        )

    }

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
        return inflater.inflate(R.layout.fragment_shopping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputLokasi : TextView =  requiredView()!!findViewById(R.id.inputLokasi)
        val inputTanggalPinjam : TextView = requiredView()!!.findViewById(R.id.inputTanggalPinjam)
        val inputTanggalKembali : TextView = requiredView()!!.findViewById(R.id.inputTanggalKembali)
        val inputModelKendaraan : TextView = requiredView()!!.findViewById(R.id.inputModelKendaraan)
        binding = btnTambah.inflate(layoutInflater)
        setContentView(binding.root)

        val btn: Button = view.findViewById(R.id.btnTambah)
        btn.setOnClickListener {
            setupListener()
            (activity as HomeActivity).changeFragment(rv_show_pemesanan())
        }

    }
}