package com.example.locksettingconfiguration

import android.app.Application
import com.example.locksettingconfiguration.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LockApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() = startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@LockApplication)
        modules(
            listOf(
                AppModule().repo,
                AppModule().viewModel,
                AppModule().dataSource,
                AppModule().database
            )
        )
    }
}