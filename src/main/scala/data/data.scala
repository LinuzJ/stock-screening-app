package data
import io.circe.Json
import io.circe.parser.parse
import scalaj.http.{Http, HttpResponse}
import java.text.SimpleDateFormat
import java.time.{LocalDate, LocalTime, ZoneOffset}
import java.util.Date


class Data(val stock: String, startDate: LocalDate, endDate: LocalDate, interval: String) {

  var source: String = s"https://query1.finance.yahoo.com/v8/finance/chart/${stock}?symbol=${stock}&period1=${startDate.toEpochSecond(LocalTime.NOON, ZoneOffset.MIN)}&period2=${endDate.toEpochSecond(LocalTime.NOON, ZoneOffset.MIN)}&interval=${interval}"

  // fetches the raw data from the origin
  private val response: Option[HttpResponse[String]] = Some(Http(source).asString)

  // parses it into readable format for circe
  private val json: Json = parse(response.get.body).getOrElse(Json.Null)
  json match {
    case Json.Null => println("fetching failed", source)
    case _ =>
  }
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

  // filtering out the Map containing price and volume data
  def getTickr: Option[String] = json.hcursor.
    downField("chart").
    downField("result").
    downArray.
    downField("symbol").
    as[String].
    toOption

  // variables containing sequences with the specific prices/volume
  private def open: Option[Seq[Double]] = Some(rawIndicators.get.filter(x => x._1 == "open")("open"))
  private def close: Option[Seq[Double]] = Some(rawIndicators.get.filter(x => x._1 == "close")("close"))
  private def volume: Option[Seq[Double]] = Some(rawIndicators.get.filter(x => x._1 == "volume")("volume").map(_.toInt))

  private var price: Seq[Seq[Double]] = deleteInvalid(List(timestamp.get.map(_.toDouble), open.get, close.get)).transpose

  // fix the date format depending on the Interval set
  def timeFormat: SimpleDateFormat = {
    val newFormat = interval match {
                      case "1m"     => "HH:mm"
                      case "2m"     => "HH:mm"
                      case "5m"     => "HH:mm"
                      case "15m"    => "dd-HH:mm"
                      case "30m"    => "dd-HH:mm"
                      case "60m"    => "dd-HH:mm"
                      case "90m"    => "dd-HH:mm"
                      case "1d"     => "MMMMMMM dd"
                      case "5d"     => "MMMMMMM dd"
                      case "1wk"    => "MMMMMMM dd"
                      case "1mo"    => "MMMMMMM"
                  }
    new SimpleDateFormat(newFormat)
  }


  // methods returning speficic data
  def getPriceData: Seq[Seq[Double]] = price

  def getVolumeData: Seq[Tuple2[String, BigInt]] = {
    timestamp.get.map(time => {
      val tf = timeFormat
      tf.format(new Date(time * 1000L))
    }) zip volume.get.map(_.toLong)
  }

  def getVolumeTotal: Int = volume.get.sum.toInt

  def getFormatted: Seq[(String, Double)] = {
    this.price.
      map(_.head).
      map(time => {
        val tempTime = new Date(time.toInt * 1000L)
        timeFormat.format(tempTime)
      }) zip
    this.price.map(_.last)
  }
  def getFormattedRelative: Seq[(String, Double)] = {
    val firstValue = this.price.head.last
    this.price.
      map(_.head).
      map(time => {
        val tempTime = new Date(time.toInt * 1000L)
        timeFormat.format(tempTime)
      }) zip
    this.price.map(x => {
      ((x.last)/firstValue)
    })
  }

  private def variance(i: Seq[Double]): Double = {
    val avg = Components.mean(i)
    i.map(a => math.pow(a - avg, 2)).sum / i.size
  }

  private def stdDev(i: Seq[Double]): Double = math.sqrt(variance(i))

  def getStdDev(d: Int): Double =  Components.roundDecimal(stdDev(getFormatted.map(_._2)), d)
  def getStdDevVol(d: Int): Double =  Components.roundDecimal(stdDev(getVolumeData.map(_._2.toLong)), d)
  def getVariance(d: Int): Double = Components.roundDecimal(variance(getFormatted.map(_._2)), d)

  // helper funtion to filter out invalid or dirty data
  private def deleteInvalid(input: Seq[Seq[Double]]): Seq[Seq[Double]] = input.filter(x => x.forall(!_.isNaN))

}
object Data {
  def generate(stock: String, startDate: LocalDate, endDate: LocalDate, interval: String): Data = {
    new Data(stock, startDate, endDate, interval)
  }
}