package com.example.ugd3_c_10898

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ugd3_c_10898.databinding.FragmentScannerBinding
import com.example.ugd3_c_10898.databinding.FragmentShoppingBinding
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import timber.log.Timber

class ScannerFragment : Fragment()  {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!
    companion object{
        private const val CAMERA_REQUEST_CODE = 100
        private const val STORAGE_REQUEST_CODE = 101
        private const val TAG = "MAIN_TAG"
    }
    private lateinit var cameraPermission: Array<String>
    private lateinit var storagePermission: Array<String>

    private var imageUri: Uri? = null

    private var barcodeScannerOption: BarcodeScannerOptions? = null

    private var barcodeScanner: BarcodeScanner? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.plant(Timber.DebugTree())
        binding.cameraBtn.setOnClickListener {
            if(checkCameraPermissions()){
                pickImageCamera()
            }else{
                requestCameraPermission()
            }
        }
        binding.galleryBtn.setOnClickListener {
            if (checkStoragePermission()){
                pickImageGallery()
            }else{
                requestStoragePermission()
            }
        }
        binding.scanBtn.setOnClickListener {
            if(imageUri == null){
                showToast("Pick image first")
            }else{
                detectResultFromImage()
            }
        }

        cameraPermission = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        storagePermission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        barcodeScannerOption = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).build()
        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOption!!)


        }

    private fun checkCameraPermissions() :Boolean {
        val resultcamera = (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)

        val resultstorage = (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)

        return resultcamera && resultstorage
    }

    private fun requestCameraPermission(){
        ActivityCompat.requestPermissions(requireActivity(), cameraPermission, CAMERA_REQUEST_CODE)
    }

    private fun checkStoragePermission() : Boolean{
        val result = (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        return result
    }

    private fun requestStoragePermission(){
        ActivityCompat.requestPermissions(requireActivity(), storagePermission, STORAGE_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            CAMERA_REQUEST_CODE -> {
                if(grantResults.isNotEmpty()){
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED

                    if(cameraAccepted && storageAccepted){
                        pickImageCamera()
                    }else{
                        showToast("Camera dan Storage permission are required")
                    }
                }
            }
            STORAGE_REQUEST_CODE -> {
                if(grantResults.isNotEmpty()){
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED

                    if(storageAccepted){
                        pickImageGallery()
                    }else{
                        showToast("Storage are required...")
                    }
                }
            }
        }

    }
    private fun showToast(message: String){
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun pickImageCamera(){
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, "Foto Sample")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Deskripsi Foto Sample")

        imageUri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        cameraActivityResultLauncer.launch(intent)
    }

    private val cameraActivityResultLauncer = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data = result.data
            Timber.d(TAG, "cameraActivityResultLauncer: imageUri: $imageUri")

            binding.imageTv.setImageURI(imageUri)
        }
    }

    private fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)

        intent.type = "image/*"
        galleryActivityResultLauncer.launch(intent)
    }

    private val galleryActivityResultLauncer = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data = result.data
            imageUri = data!!.data
            Timber.d(TAG, "galleryActivityResultLauncer: imageUri: $imageUri")
            binding.imageTv.setImageURI(imageUri)
        }else{
            showToast("Dibatalkan..........")
        }
    }
    private fun detectResultFromImage(){
        try{
            val inputImage = InputImage.fromFilePath(requireActivity(), imageUri!!)

            val barcodeResult = barcodeScanner?.process(inputImage)
                ?.addOnSuccessListener { barcodes ->
                    extractbarcodeQrCodeInfo(barcodes)
                }
                ?.addOnFailureListener{ e->
                    Timber.d(TAG, "detectResultFromImage: ", e)
                    showToast("Failed Scanning due to ${e.message}")
                }
        }catch (e: Exception){
            Timber.d(TAG, "detectResultFromImage: ", e)
            showToast("Failed Due to ${e.message}")
        }

    }

    @SuppressLint("SetTextI18n")
    private fun extractbarcodeQrCodeInfo(barcodes: List<Barcode>) {
        for (barcode in barcodes) {
            val bound = barcode.boundingBox
            val corners = barcode.cornerPoints
            val rawValue = barcode.rawValue
            Timber.d(TAG,"extractbarcodeQrCodeInfo: rawValue: $rawValue")
            val valueType = barcode.valueType
            when(valueType) {
                Barcode.TYPE_WIFI -> {
                    val typeWifi = barcode.wifi
                    val ssid = "${typeWifi?.ssid}"
                    val password = "${typeWifi?.password}"
                    var encryptionType = "${typeWifi?.encryptionType}"
                    if (encryptionType == "1"){
                        encryptionType = "OPEN"
                    } else if (encryptionType == "2"){
                        encryptionType = "WPA"
                    } else if (encryptionType == "3"){
                        encryptionType = "WEP"
                    }
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: TYPE_WIFI")
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: ssid: $ssid")
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: password:$password")
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: encryptionType:$encryptionType")
                    binding.resultView.text =
                        "TYPE_WIFI \n ssid: $ssid \npassword: $password \nencryptionType: $encryptionType" + "\n\nrawValue : $rawValue"
                }
                Barcode.TYPE_URL -> {
                    val typeUrl = barcode.url
                    val title = "${typeUrl?.title}"
                    val url = "${typeUrl?.url}"
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: TYPE_URL")
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: title: $title")
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: url: $url")
                    binding.resultView.text =
                        "TYPE_URL \ntitle: $title \nurl: $url \n\nrawValue: $rawValue"
                }
                Barcode.TYPE_EMAIL -> {
                    val typeEmail = barcode.email
                    val address = "${typeEmail?.address}"
                    val body = "${typeEmail?.body}"
                    val subject = "${typeEmail?.subject}"
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: TYPE_EMAIL")
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: address: $address")
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: body: $body")
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: subject: $subject")
                    binding.resultView.text =
                        "TYPE_EMAIL \ntitle: $address \nurl: $body \nsubject: $subject \n\nrawValue : $rawValue"
                }
                Barcode.TYPE_CONTACT_INFO -> {
                    val typeContact = barcode.contactInfo
                    val title = "${typeContact?.title}"
                    val organisasi = "${typeContact?.organization}"
                    val name = "${typeContact?.name}"
                    val phone = "${typeContact?.phones}"
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: TYPE_CONTACT_INFO")
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: Title: $title")
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: Organization: $organisasi")
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: nama: $name")
                    Timber.d(TAG,"extractbarcodeQrCodeInfo: Phone: $phone")
                    binding.resultView.text = "TYPE_CONTACT_INFO " +
                            "\ntitle: $title " +
                            "\nnama: $name " +
                            "\norganization: $organisasi " +
                            "\nPhone : $phone" + "\n\nrawValue :$rawValue"
                }
                else -> {
                    binding.resultView.text = "rawValue: $rawValue"
                }
            }
        }
    }




    }

