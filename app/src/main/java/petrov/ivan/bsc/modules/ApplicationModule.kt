package petrov.ivan.bsc.modules

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dagger.Module
import dagger.Provides
import petrov.ivan.bsc.interfaces.Singleton
import petrov.ivan.bsc.service.AssetsApi
import petrov.ivan.bsc.service.ApiProtocol
import petrov.ivan.bsc.providers.AssetsProvider
import petrov.ivan.bsc.service.ApiService
import petrov.ivan.bsc.utils.ApiConverter

@Module(includes = arrayOf(ContextModule::class))
class ApplicationModule {

    @Singleton
    @Provides
    fun getApiService(apiProtocol: ApiProtocol): ApiService {
        return ApiService(apiProtocol, getApiJsonConverter())
    }

    @Singleton
    @Provides
    fun getApiProtocolImpl(context: Context): ApiProtocol {
        return AssetsApi(getAssetsProvider(context, objectMapper()))
    }

    @Singleton
    @Provides
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().registerKotlinModule()
    }

    @Singleton
    @Provides
    fun getAssetsProvider(context: Context, objectMapper: ObjectMapper): AssetsProvider {
        return AssetsProvider(context, objectMapper)
    }

    @Singleton
    @Provides
    fun getApiJsonConverter(): ApiConverter {
        return ApiConverter()
    }
}