package petrov.ivan.bsc.webapi

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class ParamPlace(
    val address: String,
    val coord: ParamCoord
): Serializable