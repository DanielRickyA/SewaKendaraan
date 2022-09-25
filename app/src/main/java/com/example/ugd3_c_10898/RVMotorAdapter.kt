package com.example.ugd3_c_10898

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_c_10898.entity.Mobil
import com.example.ugd3_c_10898.entity.Motor

class RVMotorAdapter (private val data: Array<Motor>) : RecyclerView.Adapter<RVMotorAdapter.viewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_motor, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int){
        val curentItem = data[position]
        holder.tvNamaMotor.text = curentItem.merkMotor
        holder.tvNamaPemilikMotor.text = curentItem.namaMotor
        holder.tvImageMotor.setImageResource(curentItem.imageMotor)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNamaMotor : TextView = itemView.findViewById(R.id.txtMerkMotor)
        val tvNamaPemilikMotor : TextView = itemView.findViewById(R.id.namaMerkMotor)
        val tvImageMotor : ImageView = itemView.findViewById(R.id.imgMotor)
    }
}