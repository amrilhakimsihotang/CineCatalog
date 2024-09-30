package com.amrilhs.cinecatalog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (supportActionBar != null) {
            supportActionBar?.title = resources.getString(R.string.mydetail_string)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }




        val data = intent.getParcelableExtra<Religi>(EXTRA_DATA) as Religi
        val txtNama: TextView = findViewById(R.id.txtNama)
        val deskripsi: TextView = findViewById(R.id.txtDescripsi)
        val lokasi: TextView = findViewById(R.id.locationText)
        val kategori: TextView = findViewById(R.id.kategoriText)

        val fab: ImageView = findViewById(R.id.action_share)
        fab.setOnClickListener {
            val sMessage: String =
                resources.getString(R.string.sharedetail) + " " + data.nama + " " + resources.getString(
                    R.string.sharedetail2
                )
            val mIntent = Intent(Intent.ACTION_SEND)
            mIntent.type = "text/plain"
            mIntent.putExtra(Intent.EXTRA_TEXT, sMessage)
            startActivity(Intent.createChooser(mIntent, "Share to: "))
        }

        Glide.with(this)
            .load(data.gambar)
            .apply(RequestOptions().override(widthLong, heightLong))
            .error(R.drawable.baseline_error_24)
            .into(findViewById(R.id.idImage))
        txtNama.text = data.nama
        deskripsi.text = data.deskripsi
        lokasi.text = data.lokasi
        kategori.text = data.kategori
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
        const val widthLong: Int = 800
        const val heightLong: Int = 600
    }
}