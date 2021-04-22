package data

import java.time.LocalDate
import java.time.temporal.ChronoUnit

class TimeData {

  def getDates: Map[String, LocalDate]  = dates
  def getInterval: String               = interval

  // here you choose which dates to monitor
  private var dates: Map[String, LocalDate]   = Map("start" -> LocalDate.now.minusDays(5), "end" -> LocalDate.now)
  private var interval: String                = "60m"

  // helper method for changing the dates
  def changeDates(start: LocalDate, end: LocalDate): Unit = {
    dates = Map("start" -> start, "end" -> end)
  }
  // interval for graph (minutes) Valid intervals: [1m, 2m, 5m, 15m, 30m, 60m, 90m, 1d, 5d, 1wk, 1mo]
  def changeInterval(i: String): Unit = {
    val durationSeconds: Long = ChronoUnit.SECONDS.between(dates("start").atStartOfDay(), dates("end").atStartOfDay())
    val intervalDuration: Long = {
      i match {
        case "1m"     => 60
        case "2m"     => 120
        case "5m"     => 5*60
        case "15m"    => 15*60
        case "30m"    => 30*60
        case "60m"    => 60*60
        case "90m"    => 90*60
        case "1d"     => 24*60*60
        case "5d"     => 5*24*60*60
        case "1wk"    => 7*24*60*60
        case "1mo"    => 30*24*60*60
      }
    }
    if ((intervalDuration * 750L) < durationSeconds) {
      throw new RuntimeException("The Interval chosen is much smaller than the selected Dates, please choose a larger Interval!")
    } else if (intervalDuration < durationSeconds) {
      interval = i
    } else {
      throw new RuntimeException("Interval is larger than difference between chosen dates, please select smaller interval or change the selected dates!")
    }
  }



  // debug help
  override def toString: String = "Start date: " + dates("start") + " and End date: " + dates("end")
}
