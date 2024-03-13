package com.example.userauthenticationwithmvp.Presenter

import com.example.userauthenticationwithmvp.Model.ModelMainActivity
import com.example.userauthenticationwithmvp.View.ViewMainActivity

class PresenterMainActivity(
    private val view: ViewMainActivity,
    private val model: ModelMainActivity
) {
    fun onCreatePresenter() {
        view.onClickHandler( model.getId())
    }
}