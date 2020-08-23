package petrov.ivan.bsc.webapi

import java.io.Serializable

data class ParamRespGetParties(
    val parties: List<ParamParty>
): Serializable