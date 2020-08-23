package petrov.ivan.bsc.data

import java.io.Serializable

data class Party(
    val name: String,
    val imgUrl: String?,
    val inviter: Person,
    val listOfInvitees: List<Person>,
    val place: Place
): Serializable