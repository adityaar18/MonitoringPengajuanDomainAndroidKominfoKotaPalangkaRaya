package com.aditya.pengajuanapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import androidx.lifecycle.ViewModelProvider
import com.aditya.pengajuanapp.databinding.ActivityLoginBinding
import com.aditya.pengajuanapp.databinding.LoginDialogBinding
import com.aditya.pengajuanapp.viewmodel.LoginViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.tapadoo.alerter.Alerter

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewmodel: LoginViewModel
    private lateinit var loginDialogBinding: LoginDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        loginDialogBinding = LoginDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel = ViewModelProvider(this)[LoginViewModel::class.java]

        val loginButton = binding.login
        val regisButton = binding.Registrasi

        regisButton.setOnClickListener {
            val intent = Intent(this, RegistrasiActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val emailInput = binding.inputEmail.text.toString()
            val passwordInput = binding.password.text.toString()
            if (emailInput.isEmpty()){
                blankEmail()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
                checkEmail()
            } else if (passwordInput.isEmpty()){
                blankPassword()
            }else{
                SafetyNet.getClient(this).verifyWithRecaptcha("6LcjVlIfAAAAAAAweZL3lErfgGoNBUWv0bhyEPBS")
                    .addOnSuccessListener {
                        val userResponseToken = it.tokenResult
                        if (userResponseToken?.isNotEmpty() == true){
                            viewmodel.postLogin(emailInput, passwordInput)
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

        viewmodel.getData().observe(this){ values ->
            if (values != null){
                dialogSuccess()
            }
            if (values == null){
                Alerter.create(this)
                    .setTitle("Login Gagal")
                    .setText("Login gagal, silahkan periksa kembali email dan password")
                    .setBackgroundColorRes(R.color.blue_700)
                    .setIcon(R.drawable.ic_baseline_check_circle_24)
                    .setDuration(1000)
                    .show()
            }
        }
    }

    private fun dialogSuccess(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(loginDialogBinding.root)
        dialog.show()
        val btnClose = loginDialogBinding.btnDialog
        btnClose.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
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