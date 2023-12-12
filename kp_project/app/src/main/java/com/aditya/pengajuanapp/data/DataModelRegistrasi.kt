package com.aditya.pengajuanapp.data

import com.google.gson.annotations.SerializedName

data class DataModelRegistrasi(
    @field:SerializedName("name")
    var name : String?,
    @field:SerializedName("level")
    var level : String?,
    @field:SerializedName("email")
    var email : String?,
    @field:SerializedName("updated_at")
    var updated : String?,
    @field:SerializedName("create_at")
    var create : String?,
    @field:SerializedName("id")
    var id : String?
)
