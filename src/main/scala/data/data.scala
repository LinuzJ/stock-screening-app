package data
import io.circe.{Decoder, HCursor, Json}
import io.circe.parser.parse
import scalaj.http.{Http, HttpResponse}


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
  private val volume: Option[Seq[Int]] = Some(rawIndicators.get.filter(x => x._1 == "volume")("volume").map(_.toInt))

  private var price: Seq[Seq[Double]] = deleteInvalid(List(timestamp.get.map(_.toDouble), open.get, close.get)).transpose



  // methods returning the speficic data
  def getPriceData: Seq[Seq[Double]] = price
  def getVolumeData = List(timestamp.get, volume.get).transpose
  def getResonse = response.body



    private def deleteInvalid(input: Seq[Seq[Double]]): Seq[Seq[Double]] = input.filter(x => x.forall(!_.isNaN))
}