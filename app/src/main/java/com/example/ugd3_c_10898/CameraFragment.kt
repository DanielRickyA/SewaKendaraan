package com.example.ugd3_c_10898

import android.annotation.SuppressLint
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ugd3_c_10898.databinding.FragmentCameraBinding
import com.example.ugd3_c_10898.Profil.ProfileFragment
import java.lang.Exception

class CameraFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mCamera: Camera? = null
    private var mCameraView: CameraView? = null
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentCameraBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try{
            mCamera = Camera.open(1)
        }catch (e: Exception){
            Log.d("Error", "Failed to get Camera" + e.message)
        }
        if(mCamera !=null){
            mCameraView = CameraView(context, mCamera!!)
            val  camera_view = binding.FLCamera
            camera_view.addView(mCameraView)
        }
        @SuppressLint("MissingInflatedId", "LocalSuppress")
        val imageClose = binding.imgClose
        imageClose.setOnClickListener{
            (activity as HomeActivity).changeFragment(ProfileFragment())
        }
    }
}