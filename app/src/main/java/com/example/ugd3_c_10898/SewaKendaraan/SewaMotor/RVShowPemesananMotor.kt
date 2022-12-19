package com.example.ugd3_c_10898.SewaKendaraan.SewaMotor

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_c_10898.HomeActivity
import com.example.ugd3_c_10898.R
import com.example.ugd3_c_10898.api.TubesApi

import com.example.ugd3_c_10898.models.SewaMotor
import com.example.ugd3_c_10898.room.user.UserDB
import com.google.gson.Gson
import com.itextpdf.barcodes.BarcodeQRCode
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class RVShowPemesananMotor : Fragment() {
    lateinit var rvPemesanan: RecyclerView
    private var queue: RequestQueue? = null
     var pemesananAdapter: RVPemesananMotorAdapter = RVPemesananMotorAdapter(arrayListOf(), object: RVPemesananMotorAdapter.OnAdapterListener {
        override fun onUpdate(sewaKendaraan: SewaMotor) {
            val fragment = UpdateSewaMotorFragment()
            val bundle = Bundle()
            bundle.putInt("id", sewaKendaraan.id!!.toInt())
            fragment.arguments = bundle
            (activity as HomeActivity).changeFragment(fragment)
        }

         override fun onDownload(sewaKendaraan: SewaMotor) {
             try{
                 if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                     if (sewaKendaraan.lokasi.isEmpty() && sewaKendaraan.tanggalPinjam.isEmpty() && sewaKendaraan.tanggalKembali.isEmpty() && sewaKendaraan.merkMotor.isEmpty()){
                         Toast.makeText(requireActivity(), "Semuanya Tidak boleh kosong" , Toast.LENGTH_SHORT).show()
                     }else{
                         createPdf(sewaKendaraan)
                     }
                 }
             }catch (e: FileNotFoundException){
                 e.printStackTrace()
             }
         }

    })


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
        queue = Volley.newRequestQueue(requireContext())

        val db by lazy {UserDB(activity as HomeActivity)}

        rvPemesanan = view.findViewById(R.id.rvPemesanan)

        val btnBack: Button = view.findViewById(R.id.btnBack)


        setUpRecycleView()
        loadData()
        btnBack.setOnClickListener {
            (activity as HomeActivity).changeFragment(SewaMotorFragment())
        }
    }

    fun setUpRecycleView(){
        rvPemesanan.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pemesananAdapter
        }
    }
    fun loadData(){
        getAllSewaKendaraanMotor()
    }

    private fun getAllSewaKendaraanMotor(){
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, TubesApi.getAllSewaMotor, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                var sewa : Array<SewaMotor> = gson.fromJson(jsonObject.getJSONArray("data").toString(), Array<SewaMotor>::class.java)
                pemesananAdapter.setData(sewa.toList())
            }, Response.ErrorListener { error ->
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(requireActivity(), errors.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: Exception){
                    Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createPdf(sewaMotor: SewaMotor){
        val pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val file = File(pdfpath, "Nota_Sewa.pdf")
        FileOutputStream(file)
        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)
        pdfDocument.defaultPageSize = PageSize.A4
        document.setMargins(5f, 5f, 5f, 5f)
        @SuppressLint("UserCompatLoadingForDrawables") val d = requireActivity().getDrawable(R.drawable.foto_logo)
        val bitmap = (d as BitmapDrawable?)!!.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bitmapData = stream.toByteArray()
        val imageData = ImageDataFactory.create(bitmapData)
        val image = Image(imageData)
        val judul = Paragraph("Nota Sewa Kendaraan Motor").setBold().setFontSize(24f)
            .setTextAlignment(TextAlignment.CENTER)
        val group = Paragraph(
            """
                                    Berikut adalah
                                  Note Sewa Kendaraan Motor
                                    Jasa Cycle Fast 2022
                                """.trimIndent()).setTextAlignment(TextAlignment.CENTER).setFontSize(12f)
        val width = floatArrayOf(100f, 100f)
        val table = Table(width)
        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
        table.addCell(Cell().add(Paragraph("Nama Lokasi")))
        table.addCell(Cell().add(Paragraph(sewaMotor.lokasi)))
        table.addCell(Cell().add(Paragraph("Tanggal Pinjam")))
        table.addCell(Cell().add(Paragraph(sewaMotor.tanggalPinjam)))
        table.addCell(Cell().add(Paragraph("Tanggal Kembali")))
        table.addCell(Cell().add(Paragraph(sewaMotor.tanggalKembali)))
        table.addCell(Cell().add(Paragraph("Model Kendaraan")))
        table.addCell(Cell().add(Paragraph(sewaMotor.jenisMotor)))
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy")
        table.addCell(Cell().add(Paragraph("Tanggal Nota dibuat")))
        table.addCell(Cell().add(Paragraph(LocalDate.now().format(dateTimeFormatter))))
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:SS a")
        table.addCell(Cell().add(Paragraph("Pukul Nota dibuat")))
        table.addCell(Cell().add(Paragraph(LocalTime.now().format(timeFormatter))))

        val barcodeQRCode = BarcodeQRCode(
            """
                            ${"Lokasi: "+sewaMotor.lokasi}
                            ${"Tanggal Pinjam: "+sewaMotor.tanggalPinjam}
                            ${"Tangal Kembali: "+sewaMotor.tanggalKembali}
                            ${"Model Motor: "+sewaMotor.merkMotor}
                            ${LocalDate.now().format(dateTimeFormatter)}
                            ${LocalTime.now().format(timeFormatter)}
                                            """.trimMargin())
        val qrCodeObject = barcodeQRCode.createFormXObject(ColorConstants.BLACK, pdfDocument)
        val qrCodeImage = Image(qrCodeObject).setWidth(80f).setHorizontalAlignment(HorizontalAlignment.CENTER)


        document.add(image)
        document.add(judul)
        document.add(group)
        document.add(table)
        document.add(qrCodeImage)

        document.close()
        Toast.makeText(requireActivity(), "Pdf Created", Toast.LENGTH_LONG).show()

    }
}