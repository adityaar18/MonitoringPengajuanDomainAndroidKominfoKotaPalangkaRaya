package com.aditya.pengajuanapp.data

import com.google.gson.annotations.SerializedName

data class DataModelLogin(
    @field:SerializedName("code")
    var code : String?,
    @field:SerializedName("status")
    var status : String?,
    @field:SerializedName("message")
    var message : String?
)
