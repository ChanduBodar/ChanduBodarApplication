package com.chandubodar

import android.app.Application
import com.chandubodar.database.AppDatabase

class ChanduBodarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
    }

    companion object {
        var instance: ChanduBodarApplication? = null
    }

    init {
        instance = this
    }
}
