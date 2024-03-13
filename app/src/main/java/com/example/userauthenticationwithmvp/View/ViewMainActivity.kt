package com.example.userauthenticationwithmvp.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import com.example.userauthenticationwithmvp.androidWrapper.ActivityUtils
import com.example.userauthenticationwithmvp.androidWrapper.DeviceInfo
import com.example.userauthenticationwithmvp.databinding.ActivityMainBinding
import com.example.userauthenticationwithmvp.remote.RetrofitService
import com.example.userauthenticationwithmvp.remote.dataModel.DefaultModel
import com.example.userauthenticationwithmvp.remote.dataModel.GetApiModel
import com.example.userauthenticationwithmvp.remote.ext.ErrorUtils
import com.example.userauthenticationwithmvp.ui.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.SelectInstance
import java.lang.Exception

@SuppressLint("ViewConstructor")
class ViewMainActivity(
    contextInstance: Context,
    private val utils: ActivityUtils
) : FrameLayout(contextInstance) {

    val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))

    @SuppressLint("SuspiciousIndentation")
    fun onClickHandler(androidId: String) {

        binding.btnSend.setOnClickListener {
            val email = binding.edtInputEmail.text.toString()
            if (email.isEmpty()) {
                binding.textInputEmail.error = "Email is empty"
                return@setOnClickListener
            } else {
                binding.textInputEmail.error = null
            }
            sendCodeInEmail(email)
            binding.btnSend.visibility = INVISIBLE
            binding.textInputEmail.visibility = INVISIBLE
            binding.txtSendEmail.visibility = VISIBLE
            binding.textInputCode.visibility = VISIBLE
            binding.btnConfirm.visibility = VISIBLE
            binding.txtWrong.visibility = VISIBLE

            binding.txtSendEmail.text = "Send code to email:$email"

        }
        binding.txtWrong.setOnClickListener {
            binding.btnSend.visibility = VISIBLE
            binding.textInputEmail.visibility = VISIBLE

            binding.txtSendEmail.visibility = INVISIBLE
            binding.textInputCode.visibility = INVISIBLE
            binding.btnConfirm.visibility = INVISIBLE
            binding.txtWrong.visibility = INVISIBLE
        }
        binding.btnConfirm.setOnClickListener {
            val code = binding.edtCode.text.toString()
            val email = binding.edtInputEmail.text.toString()
            if (code.length < 6) {
                binding.textInputCode.error = "Error"
                return@setOnClickListener

            } else
                binding.textInputCode.error = null
            verifyCode(androidId, code, email)
        }

    }

    private fun sendCodeInEmail(email: String) {

        val retrofit = RetrofitService.apiSevice
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retrofit.sendRequest(email)
                if (response.isSuccessful) {
                    launch(Dispatchers.Main) {
                        val data = response.body() as DefaultModel
                        Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
                    }
                } else
                    launch(Dispatchers.Main) {
                        Toast.makeText(context, ErrorUtils.getError(response), Toast.LENGTH_SHORT)
                            .show()
                    }

            } catch (e: Exception) {
                Log.i("SERVER_ERROR", e.message.toString())
            }
        }
    }

    private fun verifyCode(androidId: String, code: String, email: String) {

        val retrofit = RetrofitService.apiSevice
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retrofit.verifyCode(androidId, code, email)
                if (response.isSuccessful) {
                    launch(Dispatchers.Main) {
                         val data = response.body() as GetApiModel
                        data.api
                        Toast.makeText(context, "your login was successful", Toast.LENGTH_LONG)
                            .show()
                        context.startActivity(Intent(context, HomeActivity::class.java))
                        utils.finished()
                    }
                } else
                    launch(Dispatchers.Main) {
                        Toast.makeText(context, ErrorUtils.getError(response), Toast.LENGTH_SHORT)
                            .show()
                    }

            } catch (e: Exception) {
                Log.i("SERVER_ERROR", e.message.toString())
            }
        }
    }
}