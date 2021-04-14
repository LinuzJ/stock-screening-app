import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.{FlowPane, GridPane, Pane}
import data.Data
import charts.Line
import charts.Pie
import charts.Bar

import java.text.SimpleDateFormat
import java.time.Instant

object Main extends JFXApp {

  // TODO
  // - Test for the fetching/name of stock while creating the data variable
  // - PieChart
  // - Column chart
  // - Data block
  // - Control Panel
  // - checkboxes for which data to show in which chart, aka a pane under the charts with relevant buttons
  // - reload data

  // timeframe for graph
  val time: Int = 20
  // interval for graph (minutes) Valid intervals: [1m, 2m, 5m, 15m, 30m, 60m, 90m, 1h, 1d, 5d, 1wk, 1mo, 3mo]
  val interval = 30
  // here you choose which stocks to monitor
  val stocks: Seq[String] = List("NOK", "GME")


  // The fetched data is stored here
  val unixTime: Long = Instant.now.getEpochSecond - (time * 24*60*60)
  val data: Seq[(String, Data)] = stocks.map{
    x =>
    (x, new Data(s"https://query1.finance.yahoo.com/v8/finance/chart/${x}?symbol=${x}&period1=${unixTime}&period2=9999999999&interval=${interval}m"))
  }

  // charts
  val scatterChart  = new Line(data)
  val pieChart      = new Pie(data)
  val barChart      = new Bar(data)


  stage = new JFXApp.PrimaryStage {

      title.value = "Data dashboard"
      val pane = new GridPane

      pane.add(scatterChart.getChart, 0, 0)
      pane.add(pieChart.getChart, 1, 0)
      pane.add(new Label("Hello"), 0, 1)
      scene = new Scene(pane, 1000, 1000)

  }


}

