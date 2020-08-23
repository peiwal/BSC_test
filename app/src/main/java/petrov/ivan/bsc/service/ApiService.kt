package petrov.ivan.bsc.service

import io.reactivex.Single
import petrov.ivan.bsc.data.Party
import petrov.ivan.bsc.utils.ApiConverter

class ApiService (val apiProtocol: ApiProtocol, val apiConverter: ApiConverter) {
    fun getParties(): Single<List<Party>> {
        return apiProtocol.getParties().map {
            apiConverter.convert(it)
        }
    }
}