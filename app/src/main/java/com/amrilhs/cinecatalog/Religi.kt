package com.amrilhs.cinecatalog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Religi(
    val nama: String,
    val deskripsi: String,
    val overview: String,
    val gambar: String,
    val kategori: String,
    val lokasi: String
): Parcelable
