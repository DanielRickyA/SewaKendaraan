package com.example.ugd3_c_10898

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVPemesanan  {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVMobilAdapter.viewHolder {
//
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_mobil, parent, false)
//        return RVMobilAdapter.viewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: RVMobilAdapter.viewHolder, position: Int){
//        val curentItem = data[position]
//        holder.tvNama.text = curentItem.merk
//        holder.tvNamaPemilik.text = curentItem.nama
//        holder.tvImageMobil.setImageResource(curentItem.image)
//    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNama : TextView = itemView.findViewById(R.id.txtMerk)
        val tvNamaPemilik : TextView = itemView.findViewById(R.id.namaMerk)
        val tvImageMobil : ImageView = itemView.findViewById(R.id.imgMobil)
    }
}