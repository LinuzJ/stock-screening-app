package data
import io.circe.Json
import io.circe.parser.parse
import scalaj.http.{Http, HttpResponse}

class Data {
  val response: HttpResponse[String] = Http("https://query1.finance.yahoo.com/v8/finance/chart/AAPL?interval=5m").asString

    val json: Json = parse(response.body).getOrElse(Json.Null)


    def getData = json
    def getResonse = response.body
}