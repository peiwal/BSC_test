package petrov.ivan.bsc.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import petrov.ivan.bsc.interfaces.Singleton

@Module
class ContextModule(internal var context: Context) {

    @Singleton
    @Provides
    fun context(): Context {
        return context.applicationContext
    }
}
