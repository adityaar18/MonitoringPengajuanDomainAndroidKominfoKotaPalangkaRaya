package com.aditya.pengajuanapp.data

import com.google.gson.annotations.SerializedName

data class DataModelPembuatan(
    @field:SerializedName("id")
    var number: String,
    @field:SerializedName("updated_at")
    var tanggal: String,
    @field:SerializedName("nama_PerangkatDaerah")
    var nama_perangkat: String,
    @field:SerializedName("kategori")
    var kategori: String,
    @field:SerializedName("nama_subdomain")
    var subdomain : String,
    @field:SerializedName("nama_WebAplikasi")
    var nama_web: String,
    @field:SerializedName("deskripsi")
    var deskripsi: String,
    @field:SerializedName("spek_web")
    var spek_web: String,
    @field:SerializedName("status")
    var status: String,
    @field:SerializedName("catatan")
    var catatan: String,
    @field:SerializedName("data_dukung")
    var data: String
)
