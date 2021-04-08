import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.{FlowPane, Pane}
import data.Data
import charts.scatter

import java.text.SimpleDateFormat
import java.time.Instant

object Main extends JFXApp {

  // TODO
  // - Test for the fetching/name of stock while creating the data variable

  // timeframe for graph (days)
  val time: Int = 2
  val unixTime: Long = Instant.now.getEpochSecond - (time * 24*60*60)
  val timeFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss")

  // interval for graph (minutes)
  val interval = 5

  // here you choose which stocks to monitor
  val stocks: Seq[String] = List("AAPL", "NOK", "BABA")
  // The fetched data is stored here
  val data: Seq[(String, Data)] = stocks.map{
    x =>
    (x, new Data(s"https://query1.finance.yahoo.com/v8/finance/chart/${x}?symbol=${x}&period1=${unixTime}&period2=9999999999&interval=${interval}m"))
  }

  // charts
  val scatterChart = new scatter(data)

  stage = new JFXApp.PrimaryStage {

      title.value = "Data dashboard"
      val pane = new FlowPane()

      pane.getChildren.add(scatterChart.getChart)
      pane.getChildren.add(new Label("Hello"))
      scene = new Scene(pane, 700, 700)

  }


}

