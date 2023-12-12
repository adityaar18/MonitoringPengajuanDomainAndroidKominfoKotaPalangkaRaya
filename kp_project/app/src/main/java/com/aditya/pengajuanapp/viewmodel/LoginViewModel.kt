package com.aditya.pengajuanapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.pengajuanapp.api.ApiConfig
import com.aditya.pengajuanapp.data.DataModelLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    val list = MutableLiveData<DataModelLogin?>()

    fun getData(): LiveData<DataModelLogin?>{
        return list
    }

    fun postLogin(email:String, password:String){
        ApiConfig.getApiService().getLogin(email, password).enqueue(object : Callback<DataModelLogin>{
            override fun onResponse(
                call: Call<DataModelLogin>,
                response: Response<DataModelLogin>
            ) {
                list.postValue(response.body())
            }

            override fun onFailure(call: Call<DataModelLogin>, t: Throwable) {
                list.postValue(null)
            }

        })
    }
}