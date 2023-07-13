package com.ngga_ring.dbmovie

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApps : Application() {
    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        var instance: MyApps = MyApps()
            private set

        val context: Context
            get() = instance
    }
}