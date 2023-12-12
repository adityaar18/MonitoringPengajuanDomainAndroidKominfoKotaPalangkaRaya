package com.aditya.pengajuanapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.pengajuanapp.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val laporanMain = binding.pengajuanMain
        laporanMain.setOnClickListener {
            val intentPengajuan = Intent(this@MainActivity, PengajuanActivity::class.java)
            startActivity(intentPengajuan)
        }

        val statusPengajuan = binding.statusMain
        statusPengajuan.setOnClickListener {
            val intentStatusPengajuan = Intent(this@MainActivity, StatusPengajuanActivity::class.java)
            startActivity(intentStatusPengajuan)
        }

        val statusWebsite = binding.statusWebMain
        statusWebsite.setOnClickListener {
            val intentStatusWebsite = Intent(this, StatusWebsiteActivity::class.java)
            startActivity(intentStatusWebsite)
        }

        val statusAplResmi = binding.monitorAppMain
        statusAplResmi.setOnClickListener {
            val intentStatusAplResmi = Intent(this, MonitoringAplikasiResmiActivity::class.java)
            startActivity(intentStatusAplResmi)
        }

        val statusAplLuar = binding.monitorAppOut
        statusAplLuar.setOnClickListener {
            val intentStatusAplLuar = Intent(this, MonitoringAplikasiLuarActivity::class.java)
            startActivity(intentStatusAplLuar)
        }

        val statusPembuatan = binding.monitorAppPembuatan
        statusPembuatan.setOnClickListener {
            val intentStatusPembuatan = Intent(this, MonitorPembutanAplikasiActivity::class.java)
            startActivity(intentStatusPembuatan)
        }

        val logoutBtn = binding.logout
        logoutBtn.setOnClickListener {
            val intentLogOut = Intent(this, LoginActivity::class.java)
            startActivity(intentLogOut)
        }

        val pertanyaan = binding.form
        pertanyaan.setOnClickListener {
            val intentPertanyaan = Intent(this, PertanyaanActivity::class.java)
            startActivity(intentPertanyaan)
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.title_main)
            .setMessage(R.string.exit_main)
            .setIcon(R.drawable.ic_baseline_exit_to_app_24)
            .setPositiveButton(R.string.exit
            ) { _, _ -> this.finishAffinity() }
            .setNegativeButton(R.string.close, null)
            .show()
    }
}