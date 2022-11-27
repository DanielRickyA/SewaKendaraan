package com.example.ugd3_c_10898

import android.app.Dialog
import android.graphics.Color.TRANSPARENT
import android.graphics.PixelFormat.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.icu.lang.UCharacter.JoiningType.TRANSPARENT
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_c_10898.entity.Mobil
import com.itextpdf.kernel.colors.Color
import com.itextpdf.kernel.pdf.canvas.wmf.MetaState.TRANSPARENT
import com.itextpdf.styledxmlparser.css.CommonCssConstants.TRANSPARENT
import com.lowagie.text.pdf.codec.wmf.MetaState.TRANSPARENT


class HomeFragment : Fragment() {

    private  lateinit var btnLottie : Button

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

//        btnLottie = view.findViewById(R.id.btnLottie)
//        btnLottie.setOnClickListener{
//            LottieDialog()
//        }
    }

    private fun LottieDialog(){
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.convertRgbToCmyk(0,0,0))
        dialog.setContentView(R.layout.rv_lottie)

        val btnTutup = dialog.findViewById<Button>(R.id.btnTutup)
        btnTutup.setOnClickListener(){
            dialog.dismiss()
        }

        dialog.show()
    }
}