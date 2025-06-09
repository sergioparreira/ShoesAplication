package com.aplication.shoesaplication

import android.app.Application
import com.aplication.maxcomposeshoes.di.repositoryModule
import com.aplication.maxcomposeshoes.di.viewModelModule
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
           modules(
                listOf(
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }

}