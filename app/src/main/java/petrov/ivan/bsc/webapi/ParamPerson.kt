package petrov.ivan.bsc.webapi

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
class ParamPerson(
    val name: String,
    val phone: String
): Serializable