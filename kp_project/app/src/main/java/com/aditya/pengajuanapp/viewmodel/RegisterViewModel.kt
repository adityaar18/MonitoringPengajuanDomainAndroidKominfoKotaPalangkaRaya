package com.aditya.pengajuanapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.pengajuanapp.api.ApiConfig
import com.aditya.pengajuanapp.data.DataModelRegistrasi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    val list = MutableLiveData<DataModelRegistrasi?>()

    fun getData(): LiveData<DataModelRegistrasi?>{
        return list
    }

    fun postRegis(name: String, email: String, password: String){
        ApiConfig.getApiService().registerUser(name, email, password).enqueue(object : Callback<DataModelRegistrasi>{
            override fun onResponse(
                call: Call<DataModelRegistrasi>,
                response: Response<DataModelRegistrasi>
            ) {
                list.postValue(response.body())
            }

            override fun onFailure(call: Call<DataModelRegistrasi>, t: Throwable) {
                list.postValue(null)
            }

        })
    }
}