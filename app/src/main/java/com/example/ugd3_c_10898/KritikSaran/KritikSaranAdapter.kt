package com.example.ugd3_c_10898.KritikSaran

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_c_10898.KritikSaran.KritikSaranActivity.Companion.LAUNCH_ADD_ACTIVITY
import com.example.ugd3_c_10898.MainActivity
import com.example.ugd3_c_10898.R

import com.example.ugd3_c_10898.models.KritikSaran
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class KritikSaranAdapter(private var KritikSaranList: List<KritikSaran>, context: Context):
    RecyclerView.Adapter<KritikSaranAdapter.ViewHolder>(), Filterable {

    private var filteredKritikSaranList: MutableList<KritikSaran>
    private val context: Context



    init {
        filteredKritikSaranList = ArrayList(KritikSaranList)
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_kritik_saran, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredKritikSaranList.size
    }

    fun setKritikSaranList(kritikSaranList: Array<KritikSaran>){
        this.KritikSaranList = kritikSaranList.toList()
        filteredKritikSaranList = kritikSaranList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kritikSaran = KritikSaranList[position]
        holder.JudulKritik.text = kritikSaran.judul
        holder.Konten.text = kritikSaran.content

        val instant = Instant.parse(kritikSaran.created_at)
        val date = LocalDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId())
        holder.Tanggal.text = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))


        holder.btnDelete.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus mahasiswa ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus"){_,_ ->
                    if (context is KritikSaranActivity) kritikSaran.id?.let { it1 -> context.deleteKritik(it1)
                    }
                }
                .show()

        }

        holder.cvKritikSaran.setOnClickListener {
            val i = Intent(context, TambahEditKritkSaran::class.java)
            i.putExtra("id", kritikSaran.id)
            if(context is KritikSaranActivity)
                context.startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }


    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<KritikSaran> = java.util.ArrayList()
                if(charSequenceString.isEmpty()){
                    filtered.addAll(KritikSaranList)
                }else{
                    for (kritikSaran in KritikSaranList){
                        if(kritikSaran.judul.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))

                        )filtered.add(kritikSaran)

                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults

            }

            override fun publishResults( CharSequence: CharSequence, filterResults: FilterResults) {
                filteredKritikSaranList.clear()
                filteredKritikSaranList.addAll(filterResults.values as List<KritikSaran>)
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var JudulKritik: TextView
        var Konten: TextView
        var Tanggal: TextView
        var btnDelete: ImageButton
        var cvKritikSaran: CardView

        init {
            JudulKritik = itemView.findViewById(R.id.judulKritik)
            Konten = itemView.findViewById(R.id.KontenKritik)
            Tanggal = itemView.findViewById(R.id.tanggalDibuat)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvKritikSaran = itemView.findViewById(R.id.cv_kritikSaran)
        }

    }
}