package com.aditya.pengajuanapp.data

import com.google.gson.annotations.SerializedName

data class DataModelPembuatanResponse(
    @SerializedName("current_page")
    val current_page: Int,
    @SerializedName("data")
    val items: ArrayList<DataModelPembuatan>
)
