package data
import io.circe.Json
import io.circe.parser.parse
import scalaj.http.{Http, HttpResponse}

class Data(source: String) {
  val response: HttpResponse[String] = Http(source).asString

    val json: Json = parse(response.body).getOrElse(Json.Null)


    def getData = json
    def getResonse = response.body
}