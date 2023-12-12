package com.aditya.pengajuanapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.pengajuanapp.R
import com.aditya.pengajuanapp.data.DataModelPembuatan
import com.aditya.pengajuanapp.databinding.TableListPembuatanBinding

class TablePembuatanAdapter: RecyclerView.Adapter<TablePembuatanAdapter.ViewHolder>() {
    private var dataList = ArrayList<DataModelPembuatan>()
    inner class ViewHolder(val binding: TableListPembuatanBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = TableListPembuatanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowPos = holder.bindingAdapterPosition

        if (rowPos == 0){
            holder.binding.apply {
                setHeaderBg(tvTanggalPecatatan)
                setHeaderBg(tvNamaPerangkat)
                setHeaderBg(tvKategoriInstansi)
                setHeaderBg(tvNamaSubdomain)
                setHeaderBg(tvNamaWebAplikasi)
                setHeaderBg(tvDeskripsi)
                setHeaderBg(tvSpekWeb)
                setHeaderBg(tvStatus)
                setHeaderBg(tvCatatan)
                setHeaderBg(tvData)
            }

            holder.binding.tvTanggalPecatatan.text = "Tanggal Pencataan Progress"
            holder.binding.tvNamaPerangkat.text = "Nama Perangkat Daerah"
            holder.binding.tvNamaSubdomain.text = "Nama Sub Domain"
            holder.binding.tvNamaWebAplikasi.text = "Nama Web/Aplikasi"
            holder.binding.tvDeskripsi.text = "Deskripsi Web/Aplikasi"
            holder.binding.tvSpekWeb.text = "Spek Web"
            holder.binding.tvStatus.text = "Status"
            holder.binding.tvCatatan.text = "Catatan"
            holder.binding.tvData.text = "Data Dukung"
        } else{
            val model = dataList[rowPos - 1]

            holder.binding.apply {
                setContentBg(tvTanggalPecatatan)
                setContentBg(tvNamaPerangkat)
                setContentBg(tvKategoriInstansi)
                setContentBg(tvNamaSubdomain)
                setContentBg(tvNamaWebAplikasi)
                setContentBg(tvDeskripsi)
                setContentBg(tvSpekWeb)
                setContentBg(tvStatus)
                setContentBg(tvCatatan)
                setContentBg(tvData)
            }

            holder.binding.tvTanggalPecatatan.text = model.tanggal
            holder.binding.tvNamaPerangkat.text = model.nama_perangkat
            holder.binding.tvNamaSubdomain.text = model.subdomain
            holder.binding.tvNamaWebAplikasi.text = model.nama_web
            holder.binding.tvDeskripsi.text = model.deskripsi
            holder.binding.tvSpekWeb.text = model.spek_web
            holder.binding.tvStatus.text = model.status
            holder.binding.tvCatatan.text = model.catatan
            holder.binding.tvData.text = model.data
        }
    }

    override fun getItemCount(): Int {
        return dataList.size + 1
    }

    private fun setHeaderBg(view: View) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg)
    }

    private fun setContentBg(view: View) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListUser(user: ArrayList<DataModelPembuatan>){
        dataList.addAll(user)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setClearList(){
        dataList.clear()
        notifyDataSetChanged()
    }
}