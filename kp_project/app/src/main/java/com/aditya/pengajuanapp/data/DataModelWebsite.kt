package com.aditya.pengajuanapp.data

import com.google.gson.annotations.SerializedName

data class DataModelWebsite (
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
    @field:SerializedName("keterangan")
    var keterangan: String,
    @field:SerializedName("update_terakhir")
    var update: String,
    @field:SerializedName("identitas")
    var logo: String,
    @field:SerializedName("profil")
    var profil: String,
    @field:SerializedName("kebijakan")
    var prioritas: String,
    @field:SerializedName("l_publik")
    var layanan_publik: String,
    @field:SerializedName("kb_produkhukum")
    var produk_hukum: String,
    @field:SerializedName("pp_kegiatan")
    var info_program: String,
    @field:SerializedName("l_aspirasi")
    var layanan_aspirasi: String,
    @field:SerializedName("kontak")
    var kontak: String,
    @field:SerializedName("catatan")
    var catatan: String
    )