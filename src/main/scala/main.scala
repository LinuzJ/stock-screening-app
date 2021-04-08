import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.Pane
import data.Data
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{CategoryAxis, NumberAxis, ScatterChart, XYChart}

import java.text.SimpleDateFormat
import java.time.Instant

object Main extends JFXApp {



  // timeframe for graph (days)
  val time: Int = 1
  val unixTime: Long = Instant.now.getEpochSecond - (time * 24*60*60)
  val timeFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss")

  // interval for graph (minutes)
  val interval = 5

  // temporary solution for creating instances of the data class
  val data1 = new Data(s"https://query1.finance.yahoo.com/v8/finance/chart/AAPL?symbol=AAPL&period1=${unixTime}&period2=9999999999&interval=${interval}m")
  val data2 = new Data(s"https://query1.finance.yahoo.com/v8/finance/chart/NOK?symbol=AAPL&period1=${unixTime}&period2=9999999999&interval=${interval}m")
  val data3 = new Data(s"https://query1.finance.yahoo.com/v8/finance/chart/AAPL?symbol=BABA&period1=${unixTime}&period2=9999999999&interval=${interval}m")

  var X                 = data1.getPriceData.map(_.head)
  val Y                 = data1.getPriceData.map(_(1))
  val X_head            = X.head
  val X_formatted = X.map(_ - X_head).map(timeFormat.format(_))

  val wrapper           = X_formatted zip Y

  stage = new JFXApp.PrimaryStage {
      title.value = "Data dashboard"
      scene = new Scene(700, 700) {
        val xAxis = CategoryAxis()
        val yAxis = NumberAxis()

        val Data = XYChart.Series[String, Number](
          "Data",
          ObservableBuffer(wrapper.map(i => XYChart.Data[String, Number](i._1, i._2)): _*))
        val plot = new ScatterChart(xAxis, yAxis)
        plot.getData().add(Data)
        root     = plot
      }
  }



//  val root = new Pane
//  val scene = new Scene(root)
//
//  stage.scene = scene
//
//
//  root.children = new Label()

}

