package com.aditya.pengajuanapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.pengajuanapp.api.ApiConfig
import com.aditya.pengajuanapp.data.DataModelAplikasiResmi
import com.aditya.pengajuanapp.data.DataModelAplikasiResmiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MonitoringAplikasiResmiViewModel : ViewModel() {
    val list = MutableLiveData<ArrayList<DataModelAplikasiResmi>>()

    fun getSearch(): LiveData<ArrayList<DataModelAplikasiResmi>>{
        return list
    }

    fun getData(number: Int){
        ApiConfig.getApiService()
            .getResmi(number)
            .enqueue(object : Callback<DataModelAplikasiResmiResponse>{
                override fun onResponse(
                    call: Call<DataModelAplikasiResmiResponse>,
                    response: Response<DataModelAplikasiResmiResponse>
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<DataModelAplikasiResmiResponse>, t: Throwable) {
                    Log.d("onFailure: ", t.message!!)
                }
            })
    }

    fun setSearch(user: String){
        ApiConfig.getApiService()
            .searchAplResmi(user)
            .enqueue(object : Callback<DataModelAplikasiResmiResponse>{
                override fun onResponse(
                    call: Call<DataModelAplikasiResmiResponse>,
                    response: Response<DataModelAplikasiResmiResponse>
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<DataModelAplikasiResmiResponse>, t: Throwable) {
                    Log.d("onFailure: ", t.message!!)
                }
            })
    }
}