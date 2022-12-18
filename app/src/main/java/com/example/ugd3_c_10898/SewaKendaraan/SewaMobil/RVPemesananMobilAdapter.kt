package com.example.ugd3_c_10898.SewaKendaraan.SewaMobil

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_c_10898.R
import com.example.ugd3_c_10898.models.SewaMobil

class RVPemesananMobilAdapter (private val data: ArrayList<SewaMobil>, private var listener: OnAdapterListener) : RecyclerView.Adapter<RVPemesananMobilAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_show_pemesanan_mobil, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int){
        val curentItem = data[position]
        holder.Lokasi.text = curentItem.lokasi
        holder.merkMobil.setText(curentItem.merkMobil)
        holder.TanggalPinjam.text = curentItem.tanggalPinjam
        holder.TanggalKembali.text = curentItem.tanggalKembali
        holder.jenismobil.text = curentItem.jenisMobil
        holder.jumlahKursiMobil.text = curentItem.jumlahKursi.toString()
        println(curentItem.merkMobil)
        holder.update.setOnClickListener{
            listener.onUpdate(curentItem)
        }

        holder.download.setOnClickListener{
            listener.onDownload(curentItem)
        }


    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Lokasi : TextView = itemView.findViewById(R.id.rvLokasi)
        val merkMobil : TextView = itemView.findViewById(R.id.rvModel)
        val TanggalPinjam : TextView = itemView.findViewById(R.id.rvTanggalPinjam)
        val TanggalKembali : TextView = itemView.findViewById(R.id.rvTanggalKembali)
        val jenismobil : TextView = itemView.findViewById(R.id.rvJenisMobil)
        val jumlahKursiMobil : TextView = itemView.findViewById(R.id.rvJumlahKursi)
        val update : ImageView = itemView.findViewById(R.id.EditBtn)
        val download : ImageView = itemView.findViewById((R.id.downloadBtn))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChange")
    fun setData(list: List<SewaMobil>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onUpdate(sewaMobil: SewaMobil)
        fun onDownload(sewaMobil: SewaMobil)

    }
}