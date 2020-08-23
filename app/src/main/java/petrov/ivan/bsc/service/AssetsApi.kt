package petrov.ivan.bsc.service

import io.reactivex.Single
import petrov.ivan.bsc.providers.AssetsProvider
import petrov.ivan.bsc.webapi.ParamRespGetParties
import java.util.concurrent.TimeUnit


class AssetsApi(val assetsProvider: AssetsProvider): ApiProtocol {
    override fun getParties(): Single<ParamRespGetParties> {
        return Single
            .fromCallable { assetsProvider.getPartiesFromJson() }
            .delay(1, TimeUnit.SECONDS) // fixme: имитация загрузки
    }
}