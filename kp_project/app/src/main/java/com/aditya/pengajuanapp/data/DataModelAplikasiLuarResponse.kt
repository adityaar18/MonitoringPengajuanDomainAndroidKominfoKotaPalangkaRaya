package com.aditya.pengajuanapp.data

import com.google.gson.annotations.SerializedName

data class DataModelAplikasiLuarResponse(
    @SerializedName("current_page")
    val current_page: Int,
    @SerializedName("data")
    val items: ArrayList<DataModelAplikasiLuar>
)
