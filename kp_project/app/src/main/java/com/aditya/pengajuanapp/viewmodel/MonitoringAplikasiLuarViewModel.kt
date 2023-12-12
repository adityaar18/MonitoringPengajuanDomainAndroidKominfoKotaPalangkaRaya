package com.aditya.pengajuanapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.pengajuanapp.api.ApiConfig
import com.aditya.pengajuanapp.data.DataModelAplikasiLuar
import com.aditya.pengajuanapp.data.DataModelAplikasiLuarResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MonitoringAplikasiLuarViewModel : ViewModel() {
    val list = MutableLiveData<ArrayList<DataModelAplikasiLuar>>()

    fun getSearch(): LiveData<ArrayList<DataModelAplikasiLuar>> {
        return list
    }

    fun getData(number: Int){
        ApiConfig.getApiService()
            .getLuar(number)
            .enqueue(object : Callback<DataModelAplikasiLuarResponse>{
                override fun onResponse(
                    call: Call<DataModelAplikasiLuarResponse>,
                    response: Response<DataModelAplikasiLuarResponse>
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<DataModelAplikasiLuarResponse>, t: Throwable) {
                    Log.d("onFailure: ", t.message!!)
                }
            })
    }

    fun setSearch(user: String){
        ApiConfig.getApiService()
            .searchAplLuar(user)
            .enqueue(object  : Callback<DataModelAplikasiLuarResponse>{
                override fun onResponse(
                    call: Call<DataModelAplikasiLuarResponse>,
                    response: Response<DataModelAplikasiLuarResponse>
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<DataModelAplikasiLuarResponse>, t: Throwable) {
                    Log.d( "onFailure: ", t.message!!)
                }

            })
    }
}