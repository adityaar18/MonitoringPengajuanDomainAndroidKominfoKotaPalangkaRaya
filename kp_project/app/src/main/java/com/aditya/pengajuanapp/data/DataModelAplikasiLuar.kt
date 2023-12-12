package com.aditya.pengajuanapp.data

import com.google.gson.annotations.SerializedName

data class DataModelAplikasiLuar(
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
    @field:SerializedName("hosting")
    var hosting: String,
    @field:SerializedName("domain")
    var domain: String,
    @field:SerializedName("ip_address")
    var ip: String,
    @field:SerializedName("metode")
    var metode: String,
    @field:SerializedName("keterangan")
    var keterangan: String,
    @field:SerializedName("update")
    var update: String,
    @field:SerializedName("versi_mobile")
    var versi: String,
    @field:SerializedName("catatan")
    var catatan: String
)
