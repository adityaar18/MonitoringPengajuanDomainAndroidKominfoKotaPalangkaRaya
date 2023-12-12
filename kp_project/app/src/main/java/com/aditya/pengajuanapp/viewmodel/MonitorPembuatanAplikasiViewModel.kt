package com.aditya.pengajuanapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.pengajuanapp.api.ApiConfig
import com.aditya.pengajuanapp.data.DataModelPembuatan
import com.aditya.pengajuanapp.data.DataModelPembuatanResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MonitorPembuatanAplikasiViewModel : ViewModel() {
    val list = MutableLiveData<ArrayList<DataModelPembuatan>>()

    fun getSearch(): LiveData<ArrayList<DataModelPembuatan>>{
        return list
    }

    fun getData(number: Int){
        ApiConfig.getApiService()
            .getPembuatan(number)
            .enqueue(object : Callback<DataModelPembuatanResponse>{
                override fun onResponse(
                    call: Call<DataModelPembuatanResponse>,
                    response: Response<DataModelPembuatanResponse>
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<DataModelPembuatanResponse>, t: Throwable) {
                    Log.d( "onFailure: ", t.message!!)
                }
            })
    }

    fun setSearch(user: String){
        ApiConfig.getApiService()
            .searchPembuatan(user)
            .enqueue(object : Callback<DataModelPembuatanResponse>{
                override fun onResponse(
                    call: Call<DataModelPembuatanResponse>,
                    response: Response<DataModelPembuatanResponse>
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<DataModelPembuatanResponse>, t: Throwable) {
                    Log.d( "onFailure: ", t.message!!)
                }

            })
    }
}