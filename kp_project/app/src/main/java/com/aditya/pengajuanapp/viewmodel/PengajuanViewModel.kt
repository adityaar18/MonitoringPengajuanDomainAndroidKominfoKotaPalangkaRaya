package com.aditya.pengajuanapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.pengajuanapp.api.ApiConfig
import com.aditya.pengajuanapp.data.DataModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengajuanViewModel: ViewModel() {
    val list = MutableLiveData<DataModel?>()

    fun getData(): LiveData<DataModel?>{
        return list
    }

    fun postData(upload: MultipartBody.Part, nama_p : RequestBody, kategori: RequestBody, jenis: RequestBody, nomor: RequestBody, tanggal_surat: RequestBody,
                 n_domain: RequestBody, n_webapp: RequestBody, desk_host: RequestBody, spek_host: RequestBody, host: RequestBody, domain: RequestBody, ip: RequestBody,
                 metode: RequestBody, spek_web: RequestBody, nama: RequestBody, kontak: RequestBody, email: RequestBody
    ){ApiConfig.getApiService().postData(upload, nama_p, kategori, jenis, nomor, tanggal_surat, n_domain, n_webapp, desk_host, spek_host, host, domain,
        ip, metode, spek_web, nama, kontak, email).enqueue(object : Callback<DataModel>{
        override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
            list.postValue(response.body())
        }

        override fun onFailure(call: Call<DataModel>, t: Throwable) {
            list.postValue(null)
        }
    })
    }
}