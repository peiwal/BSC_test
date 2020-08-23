package petrov.ivan.bsc.webapi

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class ParamParty(
    val name: String,
    val imgUrl: String?,
    val inviter: ParamPerson,
    val listOfInvitees: List<ParamPerson>,
    val place: ParamPlace
): Serializable