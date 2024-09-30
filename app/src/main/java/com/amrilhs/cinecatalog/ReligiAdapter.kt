package com.amrilhs.cinecatalog

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ReligiAdapter(private val itemReligi: ArrayList<Religi>) :
    RecyclerView.Adapter<ReligiAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Religi) {
            Glide.with(itemView.context)
                .load(item.gambar)
                .apply(RequestOptions().override(800, 600))
                .error(R.drawable.baseline_error_24)
                .into(itemView.findViewById(R.id.img_item_photo))
                 itemView.findViewById<TextView>(R.id.txtnama).text = item.nama
                 itemView.findViewById<TextView>(R.id.txtoverview).text = item.overview
                Log.d("ReligiAdapter", "Binding item: ${item.gambar}")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_list_religi, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = itemReligi.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemReligi[position])
        val data =itemReligi[position]
        holder.itemView.setOnClickListener {
            val dataIntent = Religi(
                data.nama,
                data.deskripsi,
                data.overview,
                data.gambar,
                data.kategori,
                data.lokasi

            )
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
             intent.putExtra(DetailActivity.EXTRA_DATA, dataIntent)
             holder.itemView.context.startActivity(intent)
            Log.d("intentData", "dataIntent: $dataIntent")

        }
    }
}