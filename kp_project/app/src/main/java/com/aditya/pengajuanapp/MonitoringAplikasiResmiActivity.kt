package com.aditya.pengajuanapp

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.pengajuanapp.adapter.TableAplResmiAdapter
import com.aditya.pengajuanapp.databinding.ActivityMonitoringAplikasiResmiBinding
import com.aditya.pengajuanapp.viewmodel.MonitoringAplikasiResmiViewModel
import kotlin.math.max

class MonitoringAplikasiResmiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMonitoringAplikasiResmiBinding
    private lateinit var adapter: TableAplResmiAdapter
    private lateinit var viewModel: MonitoringAplikasiResmiViewModel
    private var numberPage = 1

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitoringAplikasiResmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnBack = binding.backButton
        btnBack.setOnClickListener {
            onBackPressed()
        }

        adapter = TableAplResmiAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            recyclerViewTableList.layoutManager = LinearLayoutManager(this@MonitoringAplikasiResmiActivity)
            recyclerViewTableList.setHasFixedSize(true)
            recyclerViewTableList.adapter = adapter
        }

        viewModel = ViewModelProvider(this)[MonitoringAplikasiResmiViewModel::class.java]
        viewModel.getSearch().observe(this){search ->
            if (search != null){
                adapter.setListUser(search)
                showLoading(false)
            }
        }

        binding.previousBtn.setOnClickListener {
            adapter.setClearList()
            numberPage--
            numberPage = max(1, numberPage--)
            viewModel.getData(numberPage)
            printHalaman(numberPage)
        }

        binding.nextBtn.setOnClickListener {
            adapter.setClearList()
            numberPage++
            viewModel.getData(numberPage)
            printHalaman(numberPage)
        }

        printHalaman(numberPage)
        viewModel.getData(numberPage)
        showLoading(true)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchUser = binding.searchView
        searchUser.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchUser.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()){
                    adapter.setClearList()
                    viewModel.getData(numberPage)
                    showLoading(true)
                }else{
                    adapter.setClearList()
                    viewModel.setSearch(query)
                    showLoading(true)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()){
                    adapter.setClearList()
                    viewModel.getData(numberPage)
                    showLoading(true)
                } else{
                    adapter.setClearList()
                    viewModel.setSearch(newText)
                    showLoading(true)
                }
                return false
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun printHalaman(page: Int){
        binding.page.text = "halaman $page"
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.mainProgressBar.visibility = View.VISIBLE
        } else{
            binding.mainProgressBar.visibility = View.GONE
        }
    }
}