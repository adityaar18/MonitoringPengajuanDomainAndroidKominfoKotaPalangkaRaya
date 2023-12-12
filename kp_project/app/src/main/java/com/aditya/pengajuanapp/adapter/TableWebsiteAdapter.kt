package com.aditya.pengajuanapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.pengajuanapp.R
import com.aditya.pengajuanapp.data.DataModelWebsite
import com.aditya.pengajuanapp.databinding.TableListWebsiteBinding

class TableWebsiteAdapter: RecyclerView.Adapter<TableWebsiteAdapter.ViewHolder>() {
    private var dataList = ArrayList<DataModelWebsite>()
    inner class ViewHolder(val binding: TableListWebsiteBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = TableListWebsiteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowPos = holder.bindingAdapterPosition

        if (rowPos == 0){
            holder.itemView.apply {
                setHeaderBg(holder.binding.tvTanggalMonitor)
                setHeaderBg(holder.binding.tvNamaPerangkat)
                setHeaderBg(holder.binding.tvKategoriInstansi)
                setHeaderBg(holder.binding.tvNamaSubdomain)
                setHeaderBg(holder.binding.tvKeterangan)
                setHeaderBg(holder.binding.tvUpdate)
                setHeaderBg(holder.binding.tvLogo)
                setHeaderBg(holder.binding.tvProfil)
                setHeaderBg(holder.binding.tvPrioritas)
                setHeaderBg(holder.binding.tvLayananPublik)
                setHeaderBg(holder.binding.tvProdukHukum)
                setHeaderBg(holder.binding.tvProgramKegiatan)
                setHeaderBg(holder.binding.tvLayananAspirasi)
                setHeaderBg(holder.binding.tvKontak)
                setHeaderBg(holder.binding.tvCatatan)

                holder.binding.tvTanggalMonitor.text = "Tanggal Monitoring"
                holder.binding.tvNamaPerangkat.text = "Nama Perangkat Daerah"
                holder.binding.tvKategoriInstansi.text = "Kategori Instansi"
                holder.binding.tvNamaSubdomain.text = "Nama Sub Domain"
                holder.binding.tvKeterangan.text = "Keterangan"
                holder.binding.tvUpdate.text = "Update"
                holder.binding.tvLogo.text = "Logo"
                holder.binding.tvProfil.text = "Profil"
                holder.binding.tvPrioritas.text = "Prioritas"
                holder.binding.tvLayananPublik.text = "Info Layanan Publik"
                holder.binding.tvProdukHukum.text = "Produk Hukum"
                holder.binding.tvProgramKegiatan.text = "Info Program dan Kegiatan"
                holder.binding.tvLayananAspirasi.text = "Layanan Aspirasi"
                holder.binding.tvKontak.text = "Kontak"
                holder.binding.tvCatatan.text = "Catatan"
            }
        } else{
            val model = dataList[rowPos - 1]

            holder.binding.apply {
                setContentBg(holder.binding.tvTanggalMonitor)
                setContentBg(holder.binding.tvNamaPerangkat)
                setContentBg(holder.binding.tvKategoriInstansi)
                setContentBg(holder.binding.tvNamaSubdomain)
                setContentBg(holder.binding.tvKeterangan)
                setContentBg(holder.binding.tvUpdate)
                setContentBg(holder.binding.tvLogo)
                setContentBg(holder.binding.tvProfil)
                setContentBg(holder.binding.tvPrioritas)
                setContentBg(holder.binding.tvLayananPublik)
                setContentBg(holder.binding.tvProdukHukum)
                setContentBg(holder.binding.tvProgramKegiatan)
                setContentBg(holder.binding.tvLayananAspirasi)
                setContentBg(holder.binding.tvKontak)
                setContentBg(holder.binding.tvCatatan)

                holder.binding.tvTanggalMonitor.text = model.tanggal
                holder.binding.tvNamaPerangkat.text = model.nama_perangkat
                holder.binding.tvKategoriInstansi.text = model.kategori
                holder.binding.tvNamaSubdomain.text = model.subdomain
                holder.binding.tvKeterangan.text = model.keterangan
                holder.binding.tvUpdate.text = model.update
                holder.binding.tvLogo.text = model.logo
                holder.binding.tvProfil.text = model.profil
                holder.binding.tvPrioritas.text = model.prioritas
                holder.binding.tvLayananPublik.text = model.layanan_publik
                holder.binding.tvProdukHukum.text = model.produk_hukum
                holder.binding.tvProgramKegiatan.text = model.info_program
                holder.binding.tvLayananAspirasi.text = model.layanan_aspirasi
                holder.binding.tvKontak.text = model.kontak
                holder.binding.tvCatatan.text = model.catatan
            }
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
    fun setListUser(user: ArrayList<DataModelWebsite>){
        dataList.addAll(user)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setClearList(){
        dataList.clear()
        notifyDataSetChanged()
    }
}