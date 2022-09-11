package com.example.ugd3_c_10898

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_c_10898.entity.Mobil


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : RVMobilAdapter = RVMobilAdapter(Mobil.listOfMobil)

        val rvMobilAdapter : RecyclerView = view.findViewById(R.id.rv_home)

        rvMobilAdapter.layoutManager = layoutManager

        rvMobilAdapter.setHasFixedSize(true)

        rvMobilAdapter.adapter = adapter
    }
}