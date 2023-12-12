package com.aditya.pengajuanapp.data

import com.google.gson.annotations.SerializedName

data class DataModelPertanyaaan (
    @field: SerializedName("id")
    val id : String,
    @field: SerializedName("instansi")
    val instansi: String,
    @field: SerializedName("nama_penanya")
    val nama: String,
    @field: SerializedName("email")
    val email: String,
    @field: SerializedName("pertanyaan")
    val tanya: String,
)