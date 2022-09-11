package com.example.ugd3_c_10898

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_c_10898.entity.Mobil

class RVMobilAdapter(private val data: Array<Mobil>) :RecyclerView.Adapter<RVMobilAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_mobil, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int){
        val curentItem = data[position]
        holder.tvNama.text = curentItem.merk
        holder.tvNamaPemilik.text = curentItem.nama
        holder.tvImageMobil.setImageResource(curentItem.image)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNama : TextView = itemView.findViewById(R.id.txtMerk)
        val tvNamaPemilik : TextView = itemView.findViewById(R.id.namaMerk)
        val tvImageMobil : ImageView = itemView.findViewById(R.id.imgMobil)
    }






}