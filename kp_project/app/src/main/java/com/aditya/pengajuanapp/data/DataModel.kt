package com.aditya.pengajuanapp.data

import com.google.gson.annotations.SerializedName

data class DataModel (
    @field:SerializedName("id")
    var number: String?,
    @field:SerializedName("created_at")
    var tanggal_pengajuan: String?,
    @field:SerializedName("instansi")
    var nama_perangkat: String?,
    @field:SerializedName("kategori")
    var kategori_perangkat: String?,
    @field:SerializedName("jenis_pemohon")
    var jenis_permohonan: String?,
    @field:SerializedName("no_surat")
    var no_surat: String?,
    @field:SerializedName("tgl_surat")
    var tanggal: String?,
    @field:SerializedName("upload_surat")
    var file: String?,
    @field:SerializedName("nama_domain")
    var nama_domain: String?,
    @field:SerializedName("nama_webapk")
    var nama_app: String?,
    @field:SerializedName("dsk_webapk")
    var desc: String?,
    @field:SerializedName("spk_hosting")
    var spec: String?,
    @field:SerializedName("hosting")
    var hosting: String?,
    @field:SerializedName("domain")
    var domain: String?,
    @field:SerializedName("ip_address")
    var ip: String?,
    @field:SerializedName("metode")
    var metode: String?,
    @field:SerializedName("spek_webapp")
    var spec_web: String?,
    @field:SerializedName("nama_pengaju")
    var nama: String?,
    @field:SerializedName("kontak")
    var kontak: String?,
    @field:SerializedName("email")
    var email: String?,
    @field:SerializedName("keterangan")
    var status: String?
        )