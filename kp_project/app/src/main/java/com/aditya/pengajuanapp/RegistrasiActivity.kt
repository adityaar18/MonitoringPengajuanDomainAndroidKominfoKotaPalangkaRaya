package com.aditya.pengajuanapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aditya.pengajuanapp.databinding.ActivityRegistrasiBinding
import com.aditya.pengajuanapp.databinding.RegisDialogBinding
import com.aditya.pengajuanapp.viewmodel.RegisterViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.tapadoo.alerter.Alerter

class RegistrasiActivity : AppCompatActivity() {
    private lateinit var registrasiBinding: ActivityRegistrasiBinding
    private lateinit var viewModelRegistrasi: RegisterViewModel
    private lateinit var regisDialogBinding: RegisDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registrasiBinding = ActivityRegistrasiBinding.inflate(layoutInflater)
        regisDialogBinding = RegisDialogBinding.inflate(layoutInflater)
        setContentView(registrasiBinding.root)

        viewModelRegistrasi = ViewModelProvider(this)[RegisterViewModel::class.java]

        val registrasiButton = registrasiBinding.Registrasi

        registrasiButton.setOnClickListener {
            val nameInput = registrasiBinding.inputName.text.toString()
            val emailInput = registrasiBinding.inputEmail.text.toString()
            val passInput = registrasiBinding.password.text.toString()

            if (nameInput.isEmpty()){
                blankNama()
            }else if (emailInput.isEmpty()){
                blankEmail()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
                checkEmail()
            } else if (passInput.isEmpty()){
                blankPassword()
            }else{
                SafetyNet.getClient(this).verifyWithRecaptcha("6LcjVlIfAAAAAAAweZL3lErfgGoNBUWv0bhyEPBS")
                    .addOnSuccessListener {
                        val userResponseToken = it.tokenResult
                        if (userResponseToken?.isNotEmpty() == true){
                            viewModelRegistrasi.postRegis(nameInput, emailInput, passInput)
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

        viewModelRegistrasi.getData().observe(this){ values ->
            if (values != null){
                dialogSuccess()
            }
            if (values == null){
                Alerter.create(this)
                    .setTitle("Registrasi Gagal")
                    .setText("Registrasi gagal, silahkan periksa kembali koneksi jaringan anda")
                    .setBackgroundColorRes(R.color.blue_700)
                    .setIcon(R.drawable.ic_baseline_check_circle_24)
                    .setDuration(1000)
                    .show()
            }
        }
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

    private fun blankEmail(){
        Alerter.create(this)
            .setTitle("Email Kosong")
            .setText("Silahkan masukan email")
            .setBackgroundColorRes(R.color.blue_700)
            .setIcon(R.drawable.ic_baseline_error_24)
            .setDuration(1000)
            .show()
    }

    private fun blankPassword(){
        Alerter.create(this)
            .setTitle("Password Kosong")
            .setText("Silahkan masukan password")
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

    private fun dialogSuccess(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(regisDialogBinding.root)
        dialog.show()
        val btnClose = regisDialogBinding.btnDialog
        btnClose.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}