package com.example.locksettingconfiguration.di

import com.example.locksettingconfiguration.dataSource.LocalDataSource
import com.example.locksettingconfiguration.dataSource.RemoteDataSource
import com.example.locksettingconfiguration.repo.LockConfigRepo
import com.example.locksettingconfiguration.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppModule {

    val repo = module {
        factory { LockConfigRepo(get(), get()) }
    }

    val viewModel = module {
        viewModel { MainViewModel(get()) }
    }

    val dataSource = module {
        single { RemoteDataSource() }
        factory { LocalDataSource(get()) }
    }

    val database = module {
        single { provideDataBase(androidApplication()) }
        single { provideDao(get()) }
    }
}