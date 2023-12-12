package com.aditya.pengajuanapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.pengajuanapp.api.ApiConfig
import com.aditya.pengajuanapp.data.DataModelChartPengajuan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChartPengajuanViewModel:ViewModel(){
    val list = MutableLiveData<DataModelChartPengajuan>()

    fun returnData(): LiveData<DataModelChartPengajuan>{
        return list
    }

    fun getData(){
        ApiConfig.getApiService()
            .getChart()
            .enqueue(object : Callback<DataModelChartPengajuan>{
                override fun onResponse(
                    call: Call<DataModelChartPengajuan>,
                    response: Response<DataModelChartPengajuan>
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DataModelChartPengajuan>, t: Throwable) {
                    Log.d("onFailure: ", t.message!!)
                }
            })
    }
}