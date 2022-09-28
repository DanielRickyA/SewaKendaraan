package com.example.ugd3_c_10898

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_c_10898.room.mobil.SewaMobil
import com.example.ugd3_c_10898.room.mobil.SewaMobilDao
import com.example.ugd3_c_10898.room.user.UserDB


class rv_show_pemesanan : Fragment() {
    lateinit var rvPemesanan: RecyclerView
     var pemesananAdapter: RVPemesanan = RVPemesanan(arrayListOf(), object: RVPemesanan.OnAdapterListener{
        override fun onUpdate(sewaMobil: SewaMobil) {
            val fragment = FragmentUpdateSewaMobil()
            val bundle = Bundle()
            bundle.putInt("id", sewaMobil.id)
            fragment.arguments = bundle
            (activity as HomeActivity).changeFragment(fragment)
        }

        override fun onDelete(sewaMobil: SewaMobil) {
            (activity as HomeActivity).changeFragment(FragmentUpdateSewaMobil())
        }
    })
    lateinit var sewaDao: SewaMobilDao


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

        val db by lazy {UserDB(activity as HomeActivity)}
        sewaDao = db.SewaMobilDao()

        rvPemesanan = view.findViewById(R.id.rvPemesanan)

        val btnBack: Button = view.findViewById(R.id.btnBack)


        setUpRecycleView()
        loadData()
        btnBack.setOnClickListener {
            (activity as HomeActivity).changeFragment(ShoppingFragment())
        }
    }

    fun setUpRecycleView(){
//        pemesananAdapter = RVPemesanan(arrayListOf(), object: RVPemesanan.OnAdapterListener{
//            override fun onUpdate(sewaMobil: SewaMobil) {
//
//            }
//
//            override fun onDelete(sewaMobil: SewaMobil) {
//
//            }
//        })
        rvPemesanan.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pemesananAdapter
        }
    }
    fun loadData(){
        pemesananAdapter.setData(sewaDao.getAllData())
    }
}