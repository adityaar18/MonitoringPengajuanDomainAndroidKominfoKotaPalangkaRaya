package com.aditya.pengajuanapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.aditya.pengajuanapp.databinding.ActivityPengajuanBinding
import com.aditya.pengajuanapp.databinding.CustomDialogBinding
import com.aditya.pengajuanapp.viewmodel.PengajuanViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.tapadoo.alerter.Alerter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

class PengajuanActivity : AppCompatActivity(), UploadRequestBody.UploadCallback{
    private lateinit var binding: ActivityPengajuanBinding
    private lateinit var progress: ProgressBar
    private lateinit var pdf: Uri
    private lateinit var namePdf: String
    private lateinit var date:String
    private lateinit var kategoriItem: String
    private lateinit var permohonan: String
    private lateinit var viewModel: PengajuanViewModel
    private lateinit var bindingCustom: CustomDialogBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengajuanBinding.inflate(layoutInflater)
        bindingCustom = CustomDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PengajuanViewModel::class.java]

        val btnBack = binding.backButton
        btnBack.setOnClickListener {
            onBackPressed()
        }

        val myCalender = Calendar.getInstance()
        val btnDatePicker = binding.datePicker
        val dateShow = binding.showDate

        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val monthSetValue = month + 1
            val monthSet: String = if (monthSetValue < 10){
                "0$monthSetValue"
            }else{
                monthSetValue.toString()
            }
            val daySet: String = if (dayOfMonth < 10){
                "0$dayOfMonth"
            }else{
                dayOfMonth.toString()
            }
            dateShow.text = "TANGGAL SURAT : $dayOfMonth - $monthSet - $year"
            date = year.toString() + monthSet + daySet
        }

        btnDatePicker.setOnClickListener {
            DatePickerDialog(this, datePicker, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH),
            myCalender.get(Calendar.DAY_OF_MONTH)).show()
        }

        val documentPick = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
            if (uri == null) {
                    pdf = Uri.EMPTY
            }else{
            pdf = uri
            uri.let { returnUri ->
                contentResolver.query(returnUri, null, null, null, null)
            }?.use {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                it.moveToFirst()
                namePdf = it.getString(nameIndex)
                binding.showFile.text = "Nama File : $namePdf"
            }
            }
        }

        val btnUploadFile = binding.uploadFile
        btnUploadFile.setOnClickListener {
            documentPick.launch("application/pdf")
        }

        val spec = arrayOf("Badan/Dinas/Kantor", "Kecamatan", "Kelurahan", "UPT", "RSUD")
        val dropDownSpec = binding.inputKategori
        val adapterItems = ArrayAdapter(this, R.layout.list_item, spec)

        dropDownSpec.setAdapter(adapterItems)
        dropDownSpec.setOnItemClickListener { adapterView, _, position, _ ->
            val item = adapterView.getItemAtPosition(position).toString()
            kategoriItem = item
            Toast.makeText(applicationContext, "Kategori Perangkat Daerah : $item", Toast.LENGTH_SHORT).show()
        }

        val perm = arrayOf("1. Nama Sub Domain untuk Web Resmi Badan/Dinas/Kantor/Kec/Kelurahan/UPT/RSUD (Hosting diserver Kominfo)","2. Nama Sub Domain dan Hosting untuk Aplikasi Resmi Badan/Dinas/Kantor/Kec/Kelurahan/UPT/RSUD (Hosting diserver Kominfo)","3. Nama Sub Domain saja untuk Aplikasi Resmi Badan/Dinas/Kantor/Kec/Kelurahan/UPT (Hosting dikelola sendiri)", "4. Pembuatan Web / Aplikasi")
        val dropDownPerm = binding.inputPermohonan
        val adapterPerm = ArrayAdapter(this, R.layout.list_item, perm)

        dropDownPerm.setAdapter(adapterPerm)
        dropDownPerm.setOnItemClickListener { adapterView, _, postion, _ ->
            val item = adapterView.getItemAtPosition(postion).toString()
            Toast.makeText(applicationContext, "Jenis Permohonan : $item", Toast.LENGTH_SHORT).show()
            when (postion) {
                0 -> {
                    hidePerm2(true)
                    hidePerm3(true)
                    hidePerm4(true)
                    permohonan = "Nama Sub Domain untuk Web Resmi"
                }
                1 -> {
                    hidePerm2(false)
                    hidePerm3(true)
                    hidePerm4(true)
                    permohonan = "Nama Sub Domain dan Hosting untuk Web Resmi"
                }
                2 -> {
                    hidePerm2(true)
                    hidePerm3(false)
                    hidePerm4(true)
                    permohonan = "Nama Sub Domain saja untuk Aplikasi Resmi"
                }
                3 -> {
                    hidePerm2(true)
                    hidePerm3(true)
                    hidePerm4(false)
                    permohonan = "Pembuatan Web / Aplikasi"
                }
            }
        }

        binding.inputNama.doOnTextChanged { text, _, _, _ ->
            if (text!!.length < 2){
                binding.textInputLayout14.error = "silahkan masukan nama dengan benar atau lengkap"
            } else{
                binding.textInputLayout14.error = null
            }
        }

        binding.inputName.doOnTextChanged { text, _, _, _ ->
            if (text!!.length < 2){
                binding.textInputLayout1.error = "masukan nama perangkat daerah dengan benar"
            } else if (text.length > 2){
                binding.textInputLayout1.error = null
            }
        }

        binding.inputNoSurat.doOnTextChanged { text, _, _, _ ->
            if (text!!.length < 2){
                binding.textInputLayout4.error = "masukan nomor surat"
            }else if (text.length > 2){
                binding.textInputLayout4.error = null
            }
        }

        binding.inputNomor.doOnTextChanged { text, _, _, _ ->
            if (text!!.length < 10){
                binding.textInputLayout15.error = "nomor tidak valid"
            } else if (text.length > 10){
                binding.textInputLayout15.error = null
            }
        }

        emailFocusListener()

        val send = binding.sendBtn
        send.setOnClickListener {
            progress = binding.progressHorizontal
            progress.progress = 0

            val namaP = binding.inputName.text.toString()
            val noSurat = binding.inputNoSurat.text.toString()
            val checkSubDomain = binding.inputSubdomain.text.toString()
            val subDomain = binding.inputSubdomain.text.toString() + ".palangkaraya.go.id"
            val namaWebApp = binding.inputNamawebapp.text.toString()
            val deskripsi = binding.inputDeskripsi.text.toString()
            val spesifikasi = binding.inputSpesifikasi.text.toString()
            val namaHosting = binding.inputLayanan.text.toString()
            val namaDomain = binding.inputDomainSendiri.text.toString()
            val ip = binding.inputIP.text.toString()
            val metode = binding.inputMetode.text.toString()
            val spekWeb = binding.inputSpekWeb.text.toString()
            val nama = binding.inputNama.text.toString()
            val nomor = "+62" + binding.inputNomor.text.toString()
            val email = binding.inputEmail.text.toString()

            if (namaP.isEmpty()){
                checkNamaP()
            }else if (!this::kategoriItem.isInitialized){
                checkKategori()
            } else if (kategoriItem.isBlank()){
                blankKategori()
            }else if (!this::permohonan.isInitialized){
                checkPermohonan()
            }else if (permohonan.isBlank()){
                blankPermohonan()
            }else if (noSurat.isBlank()){
                blankSurat()
            } else if (noSurat.length < 2){
                checkSurat()
            } else if (!this::date.isInitialized){
                checkDate()
                if (binding.inputName.text.isNullOrEmpty()){
                    checkNamaP()
                }
                if (!this::kategoriItem.isInitialized){
                    checkKategori()
                }
                if (!this::permohonan.isInitialized){
                    checkPermohonan()
                }
                if (!this::pdf.isInitialized){
                    checkPdf()
                }
            }else if (date.isBlank()){
                blankDate()
            }else if (!this::date.isInitialized){
                checkPdf()
                if (binding.inputName.text.isNullOrEmpty()){
                    checkNamaP()
                }
                if (!this::kategoriItem.isInitialized){
                    checkKategori()
                }
                if (!this::permohonan.isInitialized){
                    checkPermohonan()
                }
                if (!this::date.isInitialized){
                    checkDate()
                }
            }else if (pdf == Uri.EMPTY){
                blankPdf()
            }else if (checkSubDomain.isEmpty()){
                blankSubDom()
            }else if (namaWebApp.isEmpty()){
                blankNamaApp()
            }else if (deskripsi.isEmpty()){
                blankDeskripsi()
            } else if (nama.isBlank()){
                blankNama()
            }else if(nama.length < 2){
                checkFullName()
            }else if(binding.inputNomor.text.toString().isBlank()){
                blankNomor()
            }else if(binding.inputNomor.text.toString().length < 10){
                checkNomor()
            }else if(email.isBlank()){
                blankEmail()
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                checkEmail()
            }else{
                val kategori = kategoriItem
                val inputP = permohonan
                val tanggalSurat = date

            val parcelFileDescriptor = contentResolver.openFileDescriptor(pdf, "r", null) ?: return@setOnClickListener
            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(cacheDir, namePdf)
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)

            val body = UploadRequestBody(file, "application", this)

                SafetyNet.getClient(this).verifyWithRecaptcha("6LcjVlIfAAAAAAAweZL3lErfgGoNBUWv0bhyEPBS")
                    .addOnSuccessListener {
                        val userResponseToken = it.tokenResult
                        if (userResponseToken?.isNotEmpty() == true){
                            viewModel.postData(
                                MultipartBody.Part.createFormData("upload_surat", namePdf, body),
                                namaP.toRequestBody("text/plain".toMediaTypeOrNull()),
                                kategori.toRequestBody("text/plain".toMediaTypeOrNull()),
                                inputP.toRequestBody("text/plain".toMediaTypeOrNull()),
                                noSurat.toRequestBody("text/plain".toMediaTypeOrNull()),
                                tanggalSurat.toRequestBody("text/plain".toMediaTypeOrNull()),
                                subDomain.toRequestBody("text/plain".toMediaTypeOrNull()),
                                namaWebApp.toRequestBody("text/plain".toMediaTypeOrNull()),
                                deskripsi.toRequestBody("text/plain".toMediaTypeOrNull()),
                                spesifikasi.toRequestBody("text/plain".toMediaTypeOrNull()),
                                namaHosting.toRequestBody("text/plain".toMediaTypeOrNull()),
                                namaDomain.toRequestBody("text/plain".toMediaTypeOrNull()),
                                ip.toRequestBody("text/plain".toMediaTypeOrNull()),
                                metode.toRequestBody("text/plain".toMediaTypeOrNull()),
                                spekWeb.toRequestBody("text/plain".toMediaTypeOrNull()),
                                nama.toRequestBody("text/plain".toMediaTypeOrNull()),
                                nomor.toRequestBody("text/plain".toMediaTypeOrNull()),
                                email.toRequestBody("text/plain".toMediaTypeOrNull())
                            )
                        }
                    }

                    .addOnFailureListener{
                        if (it is ApiException){
                            Alerter.create(this)
                                .setTitle("Error Captcha")
                                .setText("Error : ${CommonStatusCodes.getStatusCodeString(it.statusCode)}")
                                .setBackgroundColorRes(R.color.blue_700)
                                .setIcon(R.drawable.ic_baseline_error_24)
                                .setDuration(1000)
                                .show()
                        } else{
                            Alerter.create(this)
                                .setTitle("Error Captcha")
                                .setText("Error : ${it.message}")
                                .setBackgroundColorRes(R.color.blue_700)
                                .setIcon(R.drawable.ic_baseline_error_24)
                                .setDuration(1000)
                                .show()
                        }
                    }
            }

        }

        viewModel.getData().observe(this){values ->
            if (values != null){
                dialogSuccess()
            }
            if (values == null){
                Alerter.create(this)
                    .setTitle("Gagal Mengirim Form")
                    .setText("Gagal mengirim form, silahkan periksa kembali koneksi jaringan anda")
                    .setBackgroundColorRes(R.color.blue_700)
                    .setIcon(R.drawable.ic_baseline_check_circle_24)
                    .setDuration(1000)
                    .show()
            }
        }
    }

    private fun blankDeskripsi() {
        Alerter.create(this)
            .setTitle("Masukan Deskripsi")
            .setText("Silahkan masukan Deskripsi")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun blankNamaApp() {
        Alerter.create(this)
            .setTitle("Masukan Nama Website / Aplikasi")
            .setText("Silahkan masukan nama website / aplikasi yang diajukan")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun blankSubDom() {
        Alerter.create(this)
            .setTitle("Masukan Sub Domain")
            .setText("Silahkan masukan sub domain yang diminta")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun checkNamaP(){
        Alerter.create(this)
            .setTitle("Masukan Nama Perangkat Daerah")
            .setText("Silahkan masukan nama perangkat daerah")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun checkKategori(){
        kategoriItem = ""
        Alerter.create(this)
            .setTitle("Masukan Kategori Perangkat Daerah")
            .setText("Silahkan masukan Kategori perangkat daerah")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun blankKategori(){
        Alerter.create(this)
            .setTitle("Masukan Kategori Perangkat Daerah")
            .setText("Silahkan masukan Kategori perangkat daerah")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun checkPermohonan(){
        permohonan = ""
        Alerter.create(this)
            .setTitle("Masukan Jenis Permohonan")
            .setText("Silahkan masukan jenis permohonan yang di inginkan")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun blankPermohonan(){
        Alerter.create(this)
            .setTitle("Masukan Jenis Permohonan")
            .setText("Silahkan masukan jenis permohonan yang di inginkan")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun checkPdf(){
        pdf = Uri.EMPTY
        Alerter.create(this)
            .setTitle("Masukan File")
            .setText("Silahkan masukan file yang ingin di upload")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun blankPdf(){
        Alerter.create(this)
            .setTitle("File Kosong")
            .setText("Silahkan masukan file yang ingin di upload")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun checkDate(){
        date = ""
        Alerter.create(this)
            .setTitle("Tanggal Surat Kosong")
            .setText("Silahkan masukan tanggal surat")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun checkEmail(){
        Alerter.create(this)
            .setTitle("Email Tidak Memenuhi Syarat")
            .setText("Silahkan masukan email dengan benar")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun blankDate(){
        Alerter.create(this)
            .setTitle("Tanggal Surat Kosong")
            .setText("Silahkan masukan tanggal surat")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun blankNama(){
        Alerter.create(this)
            .setTitle("Nama Kosong")
            .setText("Silahkan masukan nama")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun blankNomor(){
        Alerter.create(this)
            .setTitle("Nomor HP/WA Kosong")
            .setText("Silahkan masukan nomor HP/WA")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun blankSurat(){
        Alerter.create(this)
            .setTitle("Nomor Surat Kosong")
            .setText("Silahkan masukan nomor surat")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun blankEmail(){
        Alerter.create(this)
            .setTitle("Email Kosong")
            .setText("Silahkan masukan email")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun checkSurat(){
        Alerter.create(this)
            .setTitle("Nomor Surat Tidak Memenuhi Persyaratan")
            .setText("Silahkan masukan nomor surat dengan lengkap")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun checkNomor(){
        Alerter.create(this)
            .setTitle("nomor HP/WA Tidak Memenuhi Persyaratan")
            .setText("Silahkan masukan nomor HP/WA")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private  fun checkFullName(){
        Alerter.create(this)
            .setTitle("Nama Tidak Memenuhi Persyaratan")
            .setText("Silahkan masukan nama lengkap")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun emailFocusListener(){
        binding.inputEmail.setOnFocusChangeListener { _, focused ->
            if (!focused){
                binding.textInputLayout16.error = errorEmail()
            }
        }
    }

    private fun errorEmail(): String? {
        val emailText = binding.inputEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return "Email tidak valid"
        }
        return null
    }

    private fun dialogSuccess(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(bindingCustom.root)
        dialog.show()
        val btnClose = bindingCustom.btnDialog
        btnClose.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun hidePerm2(state: Boolean){
        if (state){
            binding.textInputLayout8.visibility = View.GONE
            binding.inputSpesifikasi.visibility = View.GONE
        }else{
            binding.textInputLayout8.visibility = View.VISIBLE
            binding.inputSpesifikasi.visibility = View.VISIBLE
        }
    }

    private fun hidePerm3(state: Boolean){
        if (state){
            binding.textInputLayout9.visibility = View.GONE
            binding.inputLayanan.visibility = View.GONE
            binding.textInputLayout10.visibility = View.GONE
            binding.inputDomainSendiri.visibility = View.GONE
            binding.textInputLayout11.visibility = View.GONE
            binding.inputIP.visibility = View.GONE
            binding.textInputLayout12.visibility = View.GONE
            binding.inputMetode.visibility = View.GONE
        }else{
            binding.textInputLayout9.visibility = View.VISIBLE
            binding.inputLayanan.visibility = View.VISIBLE
            binding.textInputLayout10.visibility = View.VISIBLE
            binding.inputDomainSendiri.visibility = View.VISIBLE
            binding.textInputLayout11.visibility = View.VISIBLE
            binding.inputIP.visibility = View.VISIBLE
            binding.textInputLayout12.visibility = View.VISIBLE
            binding.inputMetode.visibility = View.VISIBLE
        }
    }

    private fun hidePerm4(state: Boolean){
        if (state){
            binding.textInputLayout13.visibility = View.GONE
            binding.inputSpekWeb.visibility = View.GONE
        } else{
            binding.textInputLayout13.visibility = View.VISIBLE
            binding.inputSpekWeb.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.title_exit)
            .setMessage(R.string.exit_form)
            .setIcon(R.drawable.ic_baseline_exit_to_app_24)
            .setPositiveButton(R.string.exit
            ) { _, _ -> super.onBackPressed() }
            .setNegativeButton(R.string.close, null)
            .show()
    }

    override fun onProgressUpdate(percentage: Int) {
        progress.progress = percentage
    }
}