package petrov.ivan.bsc.webapi

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class ParamCoord(
    val lat: Double,
    val lon: Double
): Serializable