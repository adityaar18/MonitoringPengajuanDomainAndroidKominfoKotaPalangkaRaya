package com.aditya.pengajuanapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.pengajuanapp.api.ApiConfig
import com.aditya.pengajuanapp.data.DataModelPertanyaaan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PertanyaanViewModel: ViewModel() {
    val list = MutableLiveData<DataModelPertanyaaan?>()

    fun getData() : LiveData<DataModelPertanyaaan?>{
        return list
    }

    fun postData(instansi: String, nama: String, email: String, pertanyaan: String){
        ApiConfig.getApiService().pertanyaan(instansi, nama, email, pertanyaan).enqueue(object : Callback<DataModelPertanyaaan>{
            override fun onResponse(
                call: Call<DataModelPertanyaaan>,
                response: Response<DataModelPertanyaaan>
            ) {
                list.postValue(response.body())
            }

            override fun onFailure(call: Call<DataModelPertanyaaan>, t: Throwable) {
                list.postValue(null)
            }
        })
    }
}