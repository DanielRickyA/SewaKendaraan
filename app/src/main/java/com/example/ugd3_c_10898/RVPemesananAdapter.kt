package com.example.ugd3_c_10898

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_c_10898.models.SewaKendaraan
import com.example.ugd3_c_10898.room.mobil.SewaMobil

class RVPemesananAdapter (private val data: ArrayList<SewaKendaraan>, private var listener: OnAdapterListener) : RecyclerView.Adapter<RVPemesananAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_show_pemesanan, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int){
        val curentItem = data[position]
        holder.Lokasi.text = curentItem.lokasi
        holder.Model.text = curentItem.modelKendaraan
        holder.TanggalPinjam.text = curentItem.tanggalPinjam
        holder.TanggalKembali.text = curentItem.tanggalKembali

        holder.update.setOnClickListener{
            listener.onUpdate(curentItem)
        }

        holder.download.setOnClickListener{
            listener.onDownload(curentItem)
        }


    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Lokasi : TextView = itemView.findViewById(R.id.rvLokasi)
        val Model : TextView = itemView.findViewById(R.id.rvModel)
        val TanggalPinjam : TextView = itemView.findViewById(R.id.rvTanggalPinjam)
        val TanggalKembali : TextView = itemView.findViewById(R.id.rvTanggalKembali)
        val update : ImageView = itemView.findViewById(R.id.EditBtn)
        val download : ImageView = itemView.findViewById((R.id.downloadBtn))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChange")
    fun setData(list: List<SewaKendaraan>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onUpdate(sewaKendaraan: SewaKendaraan)
        fun onDownload(sewaKendaraan: SewaKendaraan)

    }
}