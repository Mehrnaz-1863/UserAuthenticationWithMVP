package com.example.userauthenticationwithmvp.Model

import android.content.Context
import com.example.userauthenticationwithmvp.androidWrapper.DeviceInfo

class ModelMainActivity (private val context: Context){
    fun getId()=DeviceInfo.getAndroidId(context)
}