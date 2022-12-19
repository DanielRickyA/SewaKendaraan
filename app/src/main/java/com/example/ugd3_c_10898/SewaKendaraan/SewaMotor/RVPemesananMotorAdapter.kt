package com.example.ugd3_c_10898.SewaKendaraan.SewaMotor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_c_10898.R
import com.example.ugd3_c_10898.models.SewaMotor

class RVPemesananMotorAdapter (private val data: ArrayList<SewaMotor>, private var listener: OnAdapterListener) : RecyclerView.Adapter<RVPemesananMotorAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_show_pemesanan_motor, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int){
        val curentItem = data[position]
        holder.Lokasi.text = curentItem.lokasi
        holder.Model.text = curentItem.merkMotor
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
    fun setData(list: List<SewaMotor>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onUpdate(sewaMotor: SewaMotor)
        fun onDownload(sewaMotor: SewaMotor)

    }
}