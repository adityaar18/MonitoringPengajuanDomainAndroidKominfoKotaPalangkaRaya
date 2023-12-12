package com.aditya.pengajuanapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.pengajuanapp.R
import com.aditya.pengajuanapp.data.DataModel
import com.aditya.pengajuanapp.databinding.TableListPengajuanBinding

class  TablePengajuanAdapter : RecyclerView.Adapter<TablePengajuanAdapter.ViewHolder>() {
    private var dataList = ArrayList<DataModel>()

    inner class ViewHolder(val binding: TableListPengajuanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = TableListPengajuanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowPos = holder.bindingAdapterPosition

        if (rowPos == 0){
            holder.binding.apply {
                setHeaderBg(holder.binding.tvTanggalPengajuan)
                setHeaderBg(holder.binding.tvPerangkat)
                setHeaderBg(holder.binding.tvKategori)
                setHeaderBg(holder.binding.tvJenis)
                setHeaderBg(holder.binding.tvNoSurat)
                setHeaderBg(holder.binding.tvTanggal)
                setHeaderBg(holder.binding.tvFile)
                setHeaderBg(holder.binding.tvNamaSubdomain)
                setHeaderBg(holder.binding.tvNamaJudul)
                setHeaderBg(holder.binding.tvDesk)
                setHeaderBg(holder.binding.tvSpekHosting)
                setHeaderBg(holder.binding.tvHosting)
                setHeaderBg(holder.binding.tvDomain)
                setHeaderBg(holder.binding.tvIpAddress)
                setHeaderBg(holder.binding.tvMetode)
                setHeaderBg(holder.binding.tvSpekWeb)
                setHeaderBg(holder.binding.tvNama)
                setHeaderBg(holder.binding.tvNomor)
                setHeaderBg(holder.binding.tvEmail)
                setHeaderBg(holder.binding.tvStatus)

                holder.binding.tvTanggalPengajuan.text = "Tanggal/Waktu Pengajuan Form"
                holder.binding.tvPerangkat.text = "Nama Perangkat"
                holder.binding.tvKategori.text = "Kategori Perangkat Daerah"
                holder.binding.tvJenis.text = "Jenis Permohonan"
                holder.binding.tvNoSurat.text = "Nomor Surat"
                holder.binding.tvTanggal.text = "Tanggal Surat"
                holder.binding.tvFile.text = "File Surat"
                holder.binding.tvNamaSubdomain.text = "Nama Sub Domain"
                holder.binding.tvNamaJudul.text = "Nama/Judul Website/Aplikasi"
                holder.binding.tvDesk.text = "Deskripsi"
                holder.binding.tvSpekHosting.text = "Spesifikasi Hosting"
                holder.binding.tvHosting.text = "Hosting"
                holder.binding.tvDomain.text = "Domain"
                holder.binding.tvIpAddress.text = "IP Address"
                holder.binding.tvMetode.text = "Metode"
                holder.binding.tvSpekWeb.text = "Spesifikasi Web/Aplikasi"
                holder.binding.tvNama.text = "Nama Judul"
                holder.binding.tvNomor.text = "Nomor HP/WA"
                holder.binding.tvEmail.text = "Email"
                holder.binding.tvStatus.text = "Status"
            }
        } else{
            val model = dataList[rowPos - 1]

            holder.binding.apply {
                setContentBg(holder.binding.tvTanggalPengajuan)
                setContentBg(holder.binding.tvPerangkat)
                setContentBg(holder.binding.tvKategori)
                setContentBg(holder.binding.tvJenis)
                setContentBg(holder.binding.tvNoSurat)
                setContentBg(holder.binding.tvTanggal)
                setContentBg(holder.binding.tvFile)
                setContentBg(holder.binding.tvNamaSubdomain)
                setContentBg(holder.binding.tvNamaJudul)
                setContentBg(holder.binding.tvDesk)
                setContentBg(holder.binding.tvSpekHosting)
                setContentBg(holder.binding.tvHosting)
                setContentBg(holder.binding.tvDomain)
                setContentBg(holder.binding.tvIpAddress)
                setContentBg(holder.binding.tvMetode)
                setContentBg(holder.binding.tvSpekWeb)
                setContentBg(holder.binding.tvNama)
                setContentBg(holder.binding.tvNomor)
                setContentBg(holder.binding.tvEmail)
                setContentBg(holder.binding.tvStatus)

                holder.binding.tvTanggalPengajuan.text = model.tanggal_pengajuan
                holder.binding.tvPerangkat.text = model.nama_perangkat
                holder.binding.tvKategori.text = model.kategori_perangkat
                holder.binding.tvJenis.text = model.jenis_permohonan
                holder.binding.tvNoSurat.text = model.no_surat
                holder.binding.tvTanggal.text = model.tanggal
                holder.binding.tvFile.text = model.file
                holder.binding.tvNamaSubdomain.text = model.nama_domain
                holder.binding.tvNamaJudul.text = model.nama_app
                holder.binding.tvDesk.text = model.desc
                holder.binding.tvSpekHosting.text = model.spec
                holder.binding.tvHosting.text = model.hosting
                holder.binding.tvDomain.text = model.domain
                holder.binding.tvIpAddress.text = model.ip
                holder.binding.tvMetode.text = model.metode
                holder.binding.tvSpekWeb.text = model.spec_web
                holder.binding.tvNama.text = model.nama
                holder.binding.tvNomor.text = model.kontak
                holder.binding.tvEmail.text = model.email
                holder.binding.tvStatus.text = model.status
            }
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
    fun setListUser(user: ArrayList<DataModel>){
        dataList.addAll(user)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setClearList(){
        dataList.clear()
        notifyDataSetChanged()
    }
}