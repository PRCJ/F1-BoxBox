package com.boxbox.f1app

import android.app.Application
import com.boxbox.f1app.data.remote.KtorClient

class BoxBoxApplication : Application() {

    val ktorClient by lazy { KtorClient() }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: BoxBoxApplication
            private set
    }
}