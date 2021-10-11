package com.andriod.loginapp

import android.app.Application
import com.andriod.loginapp.model.DataProvider
import com.andriod.loginapp.model.DummyDataProvider

class LoginApplication: Application() {
    val dataProvider: DataProvider by lazy { DummyDataProvider() }

    override fun onCreate() {
        super.onCreate()
    }
}