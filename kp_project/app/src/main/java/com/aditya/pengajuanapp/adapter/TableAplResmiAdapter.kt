package com.aditya.pengajuanapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.pengajuanapp.R
import com.aditya.pengajuanapp.data.DataModelAplikasiResmi
import com.aditya.pengajuanapp.databinding.TableListAplikasiResmiBinding

class TableAplResmiAdapter: RecyclerView.Adapter<TableAplResmiAdapter.ViewHolder>() {
    private var dataList = ArrayList<DataModelAplikasiResmi>()
    inner class ViewHolder(val binding: TableListAplikasiResmiBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = TableListAplikasiResmiBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowPos = holder.bindingAdapterPosition

        if (rowPos == 0){
            holder.binding.apply {
                setHeaderBg(tvTanggalMonitor)
                setHeaderBg(tvNamaPerangkat)
                setHeaderBg(tvKategoriInstansi)
                setHeaderBg(tvNamaSubdomain)
                setHeaderBg(tvNamaWebAplikasi)
                setHeaderBg(tvDeskripsi)
                setHeaderBg(tvSpec)
                setHeaderBg(tvKeterangan)
                setHeaderBg(tvUpdate)
                setHeaderBg(tvVersi)
                setHeaderBg(tvCatatan)
            }

            holder.binding.tvTanggalMonitor.text = "Tanggal Monitor"
            holder.binding.tvNamaPerangkat.text = "Nama Perangkat"
            holder.binding.tvKategoriInstansi.text = "Kategori Instansi"
            holder.binding.tvNamaSubdomain.text = "Nama Sub Domain"
            holder.binding.tvNamaWebAplikasi.text = "Nama Web/Aplikasi"
            holder.binding.tvDeskripsi.text = "Deskripsi Web/Aplikasi"
            holder.binding.tvSpec.text = "Spek Hosting"
            holder.binding.tvKeterangan.text = "Keterangan"
            holder.binding.tvUpdate.text = "Update Terakhir"
            holder.binding.tvVersi.text = "Versi Mobile"
            holder.binding.tvCatatan.text = "Catatan"
        }else{
            val model = dataList[rowPos - 1]

            holder.binding.apply {
                setContentBg(tvTanggalMonitor)
                setContentBg(tvNamaPerangkat)
                setContentBg(tvKategoriInstansi)
                setContentBg(tvNamaSubdomain)
                setContentBg(tvNamaWebAplikasi)
                setContentBg(tvDeskripsi)
                setContentBg(tvSpec)
                setContentBg(tvKeterangan)
                setContentBg(tvUpdate)
                setContentBg(tvVersi)
                setContentBg(tvCatatan)
            }

            holder.binding.tvTanggalMonitor.text = model.tanggal
            holder.binding.tvNamaPerangkat.text = model.nama_perangkat
            holder.binding.tvKategoriInstansi.text = model.kategori
            holder.binding.tvNamaSubdomain.text = model.subdomain
            holder.binding.tvNamaWebAplikasi.text = model.nama_web
            holder.binding.tvDeskripsi.text = model.deskripsi
            holder.binding.tvSpec.text = model.spek_hosting
            holder.binding.tvKeterangan.text = model.keterangan
            holder.binding.tvUpdate.text = model.update
            holder.binding.tvVersi.text = model.versi
            holder.binding.tvCatatan.text = model.catatan
        }
    }

    private fun setHeaderBg(view: View) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg)
    }

    private fun setContentBg(view: View) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg)
    }

    override fun getItemCount(): Int {
        return dataList.size + 1
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListUser(user: ArrayList<DataModelAplikasiResmi>){
        dataList.addAll(user)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setClearList(){
        dataList.clear()
        notifyDataSetChanged()
    }
}