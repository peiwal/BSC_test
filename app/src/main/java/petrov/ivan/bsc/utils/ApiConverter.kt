package petrov.ivan.bsc.utils

import petrov.ivan.bsc.data.Coord
import petrov.ivan.bsc.data.Party
import petrov.ivan.bsc.data.Person
import petrov.ivan.bsc.data.Place
import petrov.ivan.bsc.webapi.ParamParty
import petrov.ivan.bsc.webapi.ParamPerson
import petrov.ivan.bsc.webapi.ParamPlace
import petrov.ivan.bsc.webapi.ParamRespGetParties

class ApiConverter {
    fun convert(param: ParamRespGetParties): List<Party> {
        val result =  ArrayList<Party>()

        param.parties.forEach { paramItem ->
            result.add(convert(paramItem))
        }

        return result
    }

    private fun convert(param: ParamParty): Party {
        val listOfInvitees = ArrayList<Person>()
        param.listOfInvitees.forEach {
            listOfInvitees.add(convert(it))
        }

        return Party(param.name, param.imgUrl, convert(param.inviter), listOfInvitees, convert(param.place))
    }

    private fun convert(param: ParamPerson): Person {
        return Person(param.name, param.phone, null)
    }

    private fun convert(param: ParamPlace): Place {
        return Place(param.address, Coord(param.coord.lat, param.coord.lon))
    }
}