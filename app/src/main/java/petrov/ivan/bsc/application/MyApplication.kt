package petrov.ivan.bsc.application

import android.app.Application
import petrov.ivan.bsc.components.ApplicationComponents
import petrov.ivan.bsc.components.DaggerApplicationComponents
import petrov.ivan.bsc.modules.ContextModule
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    private val appComponents: ApplicationComponents = DaggerApplicationComponents
        .builder()
        .contextModule(ContextModule(this))
        .build()

    fun getApplicationComponent() : ApplicationComponents {
        return appComponents
    }
}