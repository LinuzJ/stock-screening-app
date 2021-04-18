package components

import java.time.LocalDate

class DatesToDisplay {

  def getDates: Map[String, LocalDate] = dates

  // here you choose which dates to monitor
  private var dates: Map[String, LocalDate] = Map("start" -> LocalDate.now.minusDays(5), "end" -> LocalDate.now)

  // helper method for changing the dates
  def changeDates(start: LocalDate, end: LocalDate): Unit = {
    dates = Map("start" -> start, "end" -> end)
  }

  // debug help
  override def toString: String = "Start date: " + dates("start") + " and End date: " + dates("end")
}
