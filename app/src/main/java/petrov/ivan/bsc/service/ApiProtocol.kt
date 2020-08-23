package petrov.ivan.bsc.service

import io.reactivex.Single
import petrov.ivan.bsc.webapi.ParamRespGetParties

interface ApiProtocol{
    fun getParties(): Single<ParamRespGetParties>
}