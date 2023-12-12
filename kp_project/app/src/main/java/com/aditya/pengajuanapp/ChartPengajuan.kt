package com.aditya.pengajuanapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aditya.pengajuanapp.databinding.ActivityChartPengajuanBinding
import com.aditya.pengajuanapp.viewmodel.ChartPengajuanViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class ChartPengajuan : AppCompatActivity() {
    private lateinit var binding: ActivityChartPengajuanBinding
    private lateinit var viewModel: ChartPengajuanViewModel
    private lateinit var barList: ArrayList<BarEntry>
    private lateinit var barDataSet: BarDataSet
    private lateinit var barData: BarData
    private var data1:Float = 14.0f
    private var data2:Float = 2.0f
    private var data3:Float = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartPengajuanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ChartPengajuanViewModel::class.java]
        viewModel.getData()
        viewModel.returnData().observe(this){
            if (it != null){
                data1 = it.diterima.toFloat()
                data2 = it.diterima.toFloat()
                data3 = it.diterima.toFloat()
            }
        }
        val btnBack = binding.backButton
        btnBack.setOnClickListener {
            onBackPressed()
        }
        setBarChartValues(data1,data2, data3)
    }


    private fun setBarChartValues(data1 :Float, data2: Float, data3: Float){
        val barChart = binding.barChart
        barList = ArrayList()
        barList.add(BarEntry(1f, data1))
        barList.add(BarEntry(2f, data2))
        barList.add(BarEntry(3f, data3))
        barDataSet = BarDataSet(barList, "data")

        barData = BarData(barDataSet)

        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS, 250)

        barDataSet.valueTextColor = Color.BLACK

        barDataSet.valueTextSize = 15f

        barChart.data = barData

        barChart.description.text = "data diterima = pink, data diproses = orange, data ditolak = kuning"
    }
}