package data
import io.circe.{Decoder, HCursor, Json}
import io.circe.parser.parse
import io.circe._
import io.circe.parser._

import scalaj.http.{Http, HttpResponse}

class Data(source: String) {

  val response: HttpResponse[String] = Http(source).asString

  val json: Json = parse(response.body).getOrElse(Json.Null)

  val timestamp = json.hcursor.
    downField("chart").
    downField("result").
    downArray.
    downField("timestamp").
    as[Vector[String]].toOption

  def getData = json
  def getResonse = response.body
  def getTimestamp = timestamp
}