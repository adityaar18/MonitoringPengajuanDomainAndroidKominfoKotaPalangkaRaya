package com.aditya.pengajuanapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.pengajuanapp.api.ApiConfig
import com.aditya.pengajuanapp.data.DataModelWebsite
import com.aditya.pengajuanapp.data.DataModelWebsiteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatusWebsiteViewModel : ViewModel() {
    val list = MutableLiveData<ArrayList<DataModelWebsite>>()

    fun getSearch(): LiveData<ArrayList<DataModelWebsite>>{
        return list
    }

    fun getData(number: Int){
        ApiConfig.getApiService()
            .getWebsite(number)
            .enqueue(object : Callback<DataModelWebsiteResponse>{
                override fun onResponse(
                    call: Call<DataModelWebsiteResponse>,
                    response: Response<DataModelWebsiteResponse>
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<DataModelWebsiteResponse>, t: Throwable) {
                    Log.d( "onFailure: ", t.message!!)
                }
            })
    }

    fun setSearch(user: String){
        ApiConfig.getApiService()
            .searchWebsite(user)
            .enqueue(object : Callback<DataModelWebsiteResponse>{
                override fun onResponse(
                    call: Call<DataModelWebsiteResponse>,
                    response: Response<DataModelWebsiteResponse>
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<DataModelWebsiteResponse>, t: Throwable) {
                    Log.d("onFailure: ", t.message!!)
                }

            })
    }
}