package data
import io.circe.{Decoder, HCursor, Json}
import io.circe.parser.parse
import scalaj.http.{Http, HttpResponse}

import java.text.SimpleDateFormat


class Data(source: String) {
  // fetches the raw data from the origin
  private val response: HttpResponse[String] = Http(source).asString

  // parses it into readable format for circe
  private val json: Json = parse(response.body).getOrElse(Json.Null)

  // filtering out the timestamps
  private val timestamp: Option[Seq[Int]] = json.hcursor.
    downField("chart").
    downField("result").
    downArray.
    downField("timestamp").
    as[Seq[Int]].toOption

  // filtering out the Map containing price and volume data
  private val rawIndicators: Option[Map[String, Seq[Double]]] = json.hcursor.
    downField("chart").
    downField("result").
    downArray.
    downField("indicators").
    downField("quote").
    downArray.
    as[Map[String, Seq[Double]]].
    toOption


  // variables containing sequences with the specific prices/volume
  private val open: Option[Seq[Double]] = Some(rawIndicators.get.filter(x => x._1 == "open")("open"))
  private val close: Option[Seq[Double]] = Some(rawIndicators.get.filter(x => x._1 == "close")("close"))
  private val volume: Option[Seq[Double]] = Some(rawIndicators.get.filter(x => x._1 == "volume")("volume").map(_.toInt))

  private var price: Seq[Seq[Double]] = deleteInvalid(List(timestamp.get.map(_.toDouble), open.get, close.get)).transpose

  val timeFormat = new SimpleDateFormat("HH:mm:ss")

  // methods returning the speficic data
  def getPriceData: Seq[Seq[Double]] = price

  def getVolumeData: Seq[Tuple2[String, Double]] = timestamp.get.map(timeFormat.format(_)) zip volume.get

  def getVolumeTotal: Int = volume.get.sum.toInt

  def getResonse = response.body

  def getFormatted: Seq[(String, Double)] = {
    this.price.
      map(_.head).
      map(timeFormat.format(_)) zip
    this.price.map(_(1))
  }

  // helper funtion to filter out invalid or dirty data
  private def deleteInvalid(input: Seq[Seq[Double]]): Seq[Seq[Double]] = input.filter(x => x.forall(!_.isNaN))
}