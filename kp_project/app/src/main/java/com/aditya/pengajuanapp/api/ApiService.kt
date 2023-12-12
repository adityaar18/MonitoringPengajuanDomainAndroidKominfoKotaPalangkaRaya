package com.aditya.pengajuanapp.api

import com.aditya.pengajuanapp.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("Contact")
    fun pertanyaan(
        @Field("instansi") instansi: String?,
        @Field("nama_penanya") nama: String?,
        @Field("email") email: String?,
        @Field("pertanyaan") pertanyaan: String?,
    ):Call<DataModelPertanyaaan>

    @FormUrlEncoded
    @POST("PostLogin")
    fun getLogin(
        @Field("email")  email: String,
        @Field("password") pass: String
    ):Call<DataModelLogin>

    @FormUrlEncoded
    @POST("simpanUser")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") pass: String
    ):Call<DataModelRegistrasi>

    @GET("Pengajuan")
    fun getData(
        @Query("page") page: Int
    ):Call<DataModelResponse>

    @GET("Data_SubdomainWeb")
    fun getWebsite(
        @Query("page") page: Int
    ):Call<DataModelWebsiteResponse>

    @GET("Data_SubdomainHosting")
    fun getResmi(
        @Query("page") page: Int
    ):Call<DataModelAplikasiResmiResponse>

    @GET("Data_Subdomain")
    fun getLuar(
        @Query("page") page: Int
    ):Call<DataModelAplikasiLuarResponse>

    @GET("Data_PembuatanWebAplikasi")
    fun getPembuatan(
        @Query("page") page: Int
    ):Call<DataModelPembuatanResponse>

    @GET("Chart")
    fun getChart():Call<DataModelChartPengajuan>

    @Multipart
    @POST("posts")
    fun postData(
        @Part upload_surat: MultipartBody.Part?,
        @Part("instansi") nama_perangkat: RequestBody?,
        @Part("kategori") kategori: RequestBody?,
        @Part("jenis_pemohon") jenis_permohonan: RequestBody?,
        @Part("no_surat") nomor_surat: RequestBody?,
        @Part("tgl_surat") tanggal_surat: RequestBody?,
        @Part("nama_domain") nama_domain: RequestBody?,
        @Part("nama_webapk") nama_webaplikasi: RequestBody?,
        @Part("dsk_webapk") deskripsi_hosting: RequestBody?,
        @Part("spk_hosting") spesifikasi_hosting: RequestBody?,
        @Part("hosting") hosting: RequestBody?,
        @Part("domain") domain: RequestBody?,
        @Part("ip_address") ip_address: RequestBody?,
        @Part("metode") metode: RequestBody?,
        @Part("spek_webapp") spek_website: RequestBody?,
        @Part("nama_pengaju") nama: RequestBody?,
        @Part("kontak") kontak: RequestBody?,
        @Part("email") email: RequestBody?,
    ):Call<DataModel>

    @GET("SearchPengajuan/{instansi}")
    fun searchData(
        @Path("instansi") nama_perangkat: String?
    ):Call<DataModelResponse>

    @GET("SearchSubdomainWeb/{nama_PerangkatDaerah}")
    fun searchWebsite(
        @Path("nama_PerangkatDaerah") instansi: String?
    ):Call<DataModelWebsiteResponse>

    @GET("SearchSubdomainHosting/{nama_PerangkatDaerah}")
    fun searchAplResmi(
        @Path("nama_PerangkatDaerah") instansi: String?
    ):Call<DataModelAplikasiResmiResponse>

    @GET("SearchSubdomain/{nama_PerangkatDaerah}")
    fun searchAplLuar(
        @Path("nama_PerangkatDaerah") instansi: String?
    ):Call<DataModelAplikasiLuarResponse>

    @GET("SearchProgress/{instansi}")
    fun searchPembuatan(
        @Path("instansi") instansi: String?
    ):Call<DataModelPembuatanResponse>
}