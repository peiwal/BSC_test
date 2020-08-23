package petrov.ivan.bsc.providers

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import petrov.ivan.bsc.webapi.*

class AssetsProvider(val context: Context, val objectMapper: ObjectMapper) {
    fun getPartiesFromJson(): ParamRespGetParties {
        val json = loadJSONFromAssets("party.json")
        return objectMapper.readValue(json, ParamRespGetParties::class.java)
    }

    private fun loadJSONFromAssets(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { reader ->
            reader.readText()
        }
    }

    private fun createJson(): String  {
        val obj =  ParamRespGetParties(listOf(
            ParamParty("Именины", "https://i.imgur.com/mBEM1Pw.jpg",
                ParamPerson("Кристина", "+79199003394"),
                listOf(
                    ParamPerson("Олег", "+79199440688")
                    , ParamPerson("Вадим", "+79448375143")
                    , ParamPerson("Александра", "+79827775144")),
                ParamPlace("Mocква, ГУМ", ParamCoord(55.75429,37.61931))
            ),
            ParamParty("Xbox party", "https://i.imgur.com/iCrSlL1.jpg",
                ParamPerson("Савелий", "+79199067688"),
                listOf(
                    ParamPerson("Игорь", "+79828376743")
                    , ParamPerson("Марат", "+79828675144")),
                ParamPlace("Ижевск, ул. Пушкинсая 268", ParamCoord(56.86309,53.20820))
            ),
            ParamParty("Gangsta party", "https://i.imgur.com/lclksvB.png",
                ParamPerson("Джеймс", "+79199067688"),
                listOf(
                    ParamPerson("Юлия", "+79828345143")),
                ParamPlace("Ижевск, ул. Циолковскогог 13", ParamCoord(56.83740,53.24239))
            ),
            ParamParty("Obama Birthday", "https://i.imgur.com/QixjNM8.jpg",
                ParamPerson("Иван", "+7919999988"),
                listOf(
                    ParamPerson("Екатерина", "+7982853443")
                    , ParamPerson("Снежана", "+79828359044")),
                ParamPlace("Вашингтон, Белый дом", ParamCoord(38.89690,-77.03707))
            ),
            ParamParty("Дискотека", "https://i.imgur.com/2TBpeV6.jpg",
                ParamPerson("Николай", "+79199012688"),
                listOf(
                    ParamPerson("Артемий", "+79828674243")
                    , ParamPerson("Надежда", "+79828388924")),
                ParamPlace("Париж, Эйфелева башня", ParamCoord(48.85829,2.29378))
            )))

        return objectMapper.writeValueAsString(obj)
    }
}