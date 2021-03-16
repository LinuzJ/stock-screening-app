package data
import io.circe.{Decoder, HCursor, Json}
import io.circe.parser.parse
import scalaj.http.{Http, HttpResponse}

import scala.collection.mutable

class Data(source: String) {

  private val response: HttpResponse[String] = Http(source).asString

  private val json: Json = parse(response.body).getOrElse(Json.Null)

  private val timestamp: Option[Seq[Int]] = json.hcursor.
    downField("chart").
    downField("result").
    downArray.
    downField("timestamp").
    as[Seq[Int]].toOption

  private val rawIndicators: Option[Map[String, Seq[Double]]] = json.hcursor.
    downField("chart").
    downField("result").
    downArray.
    downField("indicators").
    downField("quote").
    downArray.
    as[Map[String, Seq[Double]]].
    toOption

  private val open: Option[Seq[Double]] = Some(rawIndicators.get.filter(x => x._1 == "open")("open"))
  private val close: Option[Seq[Double]] = Some(rawIndicators.get.filter(x => x._1 == "close")("close"))
  private val volume: Option[Seq[Int]] = Some(rawIndicators.get.filter(x => x._1 == "volume")("volume").map(_.toInt))

  private val data = List(timestamp.get, open.get, close.get, volume.get)


  def getData = data.transpose
  def getResonse = response.body

}