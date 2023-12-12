package com.aditya.pengajuanapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.pengajuanapp.api.ApiConfig
import com.aditya.pengajuanapp.data.DataModel
import com.aditya.pengajuanapp.data.DataModelResponse
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatusPengajuanViewModel: ViewModel() {
    val list = MutableLiveData<ArrayList<DataModel>>()


    fun getSearch(): LiveData<ArrayList<DataModel>>{
        return list
    }

    fun getData(number: Int){
        ApiConfig.getApiService()
            .getData(number)
            .enqueue(object : Callback<DataModelResponse>{
                override fun onResponse(
                    call: Call<DataModelResponse>,
                    response: Response<DataModelResponse>
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<DataModelResponse>, t: Throwable) {
                    Log.d("onFailure: ", t.message!!)
                }

            })
    }

    fun setSearch(user: String){
        ApiConfig.getApiService()
            .searchData(user)
            .enqueue(object : Callback<DataModelResponse>{
                override fun onResponse(
                    call: Call<DataModelResponse>,
                    response: Response<DataModelResponse>
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<DataModelResponse>, t: Throwable) {
                    Log.d("onFailure: ", t.message!!)
                }
            })
    }
}