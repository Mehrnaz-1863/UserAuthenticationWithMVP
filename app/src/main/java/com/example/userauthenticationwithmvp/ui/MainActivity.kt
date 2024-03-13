package com.example.userauthenticationwithmvp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.userauthenticationwithmvp.Model.ModelMainActivity
import com.example.userauthenticationwithmvp.Presenter.PresenterMainActivity
import com.example.userauthenticationwithmvp.R
import com.example.userauthenticationwithmvp.View.ViewMainActivity
import com.example.userauthenticationwithmvp.androidWrapper.ActivityUtils

class MainActivity : AppCompatActivity(),ActivityUtils {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ViewMainActivity(this,this)
        setContentView(view.binding.root)

        val presenter = PresenterMainActivity(view, ModelMainActivity(this))
        presenter.onCreatePresenter()

    }

    override fun finished() {
        finish()
    }
}