package com.example.ugd3_c_10898.SewaKendaraan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ugd3_c_10898.HomeActivity
import com.example.ugd3_c_10898.SewaKendaraan.SewaMobil.SewaMobilFragment
import com.example.ugd3_c_10898.SewaKendaraan.SewaMotor.SewaMotorFragment
import com.example.ugd3_c_10898.databinding.FragmentSewaKendaraanBinding


class SewaKendaraanFragment : Fragment() {
    private var _binding: FragmentSewaKendaraanBinding? =null
    private val binding get() = _binding!!
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
        _binding = FragmentSewaKendaraanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgMobil.setOnClickListener{
            (activity as HomeActivity).changeFragment(SewaMobilFragment())
        }

        binding.imgMotor.setOnClickListener{
            (activity as HomeActivity).changeFragment(SewaMotorFragment())
        }
    }

}