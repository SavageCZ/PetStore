package cz.mendelu.pef.compose.petstore

import android.app.Application
import android.content.Context
import cz.mendelu.pef.compose.petstore.di.apiModule
import cz.mendelu.pef.compose.petstore.di.remoteRepositoryModule
import cz.mendelu.pef.compose.petstore.di.retrofitModule
import cz.mendelu.pef.compose.petstore.di.viewModelModule
import org.koin.android.BuildConfig

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PetsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@PetsApplication)
            modules(listOf(
                apiModule, retrofitModule, remoteRepositoryModule, viewModelModule
            ))
        }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }

}