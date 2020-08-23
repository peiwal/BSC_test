package petrov.ivan.bsc.components

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Component
import petrov.ivan.bsc.interfaces.Singleton
import petrov.ivan.bsc.modules.ApplicationModule
import petrov.ivan.bsc.service.ApiService


@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponents {
    fun getApiService(): ApiService
    fun getObjectMapper(): ObjectMapper
}