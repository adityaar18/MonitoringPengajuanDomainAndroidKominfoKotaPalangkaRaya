package com.aditya.pengajuanapp.data

import com.google.gson.annotations.SerializedName

data class DataModelChartPengajuan(
    @field:SerializedName("diterima")
    var diterima : String,
    @field:SerializedName("proses")
    var proses : String,
    @field:SerializedName("ditolak")
    var ditolak : String
        )