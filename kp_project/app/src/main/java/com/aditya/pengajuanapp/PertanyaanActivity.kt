package com.aditya.pengajuanapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.aditya.pengajuanapp.databinding.ActivityPertanyaanBinding
import com.aditya.pengajuanapp.databinding.TanyaDialogBinding
import com.aditya.pengajuanapp.viewmodel.PertanyaanViewModel
import com.tapadoo.alerter.Alerter

class PertanyaanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPertanyaanBinding
    private lateinit var bindingtanya: TanyaDialogBinding
    private lateinit var viewModel: PertanyaanViewModel
    private var message = "Saya ingin bertanya...."
    private var number = "+6281349013529"
    private var email = "doramey@gmail.com"
    private var subject = "Pertanyaan Swap Data Pengajuan Sub Domain dan Monitoring"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPertanyaanBinding.inflate(layoutInflater)
        bindingtanya = TanyaDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PertanyaanViewModel::class.java]

        val sendBtn = binding.sendBtn

        val btnBack = binding.backButton
        btnBack.setOnClickListener {
            onBackPressed()
        }

        val wa = binding.whatsapp
        wa.setOnClickListener {
            try {
                val uri = Uri.parse("https://api.whatsapp.com/send?phone=$number&text=$message")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.`package` = "com.whatsapp"
                startActivity(intent)
            }catch (ex: ActivityNotFoundException){
                checkWhatsapp()
            }
        }

        val mail = binding.mail
        mail.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:$email")
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, message)
                }
                startActivity(intent)
            }catch (ex : ActivityNotFoundException){
                checkMailApp()
            }
        }

        binding.inputPerangkat.doOnTextChanged { text, _, _, _ ->
            if (text!!.length < 2){
                binding.textInputLayout2.error = "masukan nama perangkat daerah dengan benar"
            } else if (text.length > 2){
                binding.textInputLayout2.error = null
            }
        }

        binding.inputName.doOnTextChanged { text, _, _, _ ->
            if (text!!.length < 2){
                binding.textInputLayout1.error = "masukan nama  dengan benar"
            } else if (text.length > 2){
                binding.textInputLayout1.error = null
            }
        }

        emailFocusListener()

        sendBtn.setOnClickListener {
            val nama = binding.inputName.text.toString()
            val perangkat = binding.inputPerangkat.text.toString()
            val email = binding.inputEmail.text.toString()
            val pesan = binding.inputDeskripsi.text.toString()

            if (nama.isEmpty()){
                blankNama()
            } else if(nama.length < 2){
                checkFullName()
            } else if (perangkat.isEmpty()){
                checkNamaP()
            }
            else if(email.isEmpty()){
                blankEmail()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                checkEmail()
            }else  if (pesan.isEmpty()){
                checkPesan()
            }else{
                viewModel.postData(nama, perangkat, email, pesan)
            }
        }

        viewModel.getData().observe(this){values ->
            if (values != null){
                dialogSuccess()
            }
            if (values == null){
                Alerter.create(this)
                    .setTitle("Pertanyaan Gagal Terkirim")
                    .setText("Pertanyaan gagal terkirim, silahkan periksa kembali koneksi jaringan anda")
                    .setBackgroundColorRes(R.color.blue_700)
                    .setIcon(R.drawable.ic_baseline_check_circle_24)
                    .setDuration(1000)
                    .show()
            }
        }
    }

    private fun dialogSuccess() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(bindingtanya.root)
        dialog.show()
        val btnClose = bindingtanya.btnDialog
        btnClose.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkWhatsapp(){
        Alerter.create(this)
            .setTitle("Aplikasi Whatsapp Tidak Terinstall")
            .setText("Silahkan install aplikasi whatsapp")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun checkMailApp(){
        Alerter.create(this)
            .setTitle("Aplikasi Email Tidak Terinstall")
            .setText("Silahkan install aplikasi Email")
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

    private fun blankNama(){
        Alerter.create(this)
            .setTitle("Nama Kosong")
            .setText("Silahkan masukan nama")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun checkPesan(){
        Alerter.create(this)
            .setTitle("Masukan Pesan")
            .setText("Silahkan masukan Pesan")
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

    private fun blankEmail(){
        Alerter.create(this)
            .setTitle("Email Kosong")
            .setText("Silahkan masukan email")
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

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.title_pertanyaan)
            .setMessage(R.string.exit_pertanyaan)
            .setIcon(R.drawable.ic_baseline_exit_to_app_24)
            .setPositiveButton(R.string.exit
            ) { _, _ -> super.onBackPressed() }
            .setNegativeButton(R.string.close, null)
            .show()
    }

    private fun emailFocusListener(){
        binding.inputEmail.setOnFocusChangeListener { _, focused ->
            if (!focused){
                binding.textInputLayout3.error = errorEmail()
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
}