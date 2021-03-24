import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.Pane
import data.Data
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{NumberAxis, ScatterChart, XYChart}

import java.time.Instant

object Main extends JFXApp {



  // timeframe for graph (days)
  val time: Int = 4
  val unixTime: Long = Instant.now.getEpochSecond - (time * 24*60*60)

  // interval for graph (minutes)
  val interval = 5

  // temporary solution for creating instances of the data class
  val data1 = new Data(s"https://query1.finance.yahoo.com/v8/finance/chart/AAPL?symbol=AAPL&period1=${unixTime}&period2=9999999999&interval=${interval}m")
  val data2 = new Data(s"https://query1.finance.yahoo.com/v8/finance/chart/NOK?symbol=AAPL&period1=${unixTime}&period2=9999999999&interval=${interval}m")
  val data3 = new Data(s"https://query1.finance.yahoo.com/v8/finance/chart/AAPL?symbol=BABA&period1=${unixTime}&period2=9999999999&interval=${interval}m")

  val X                 = data1.getPriceData.map(_.head)
  val Y                 = data1.getPriceData.map(_(1))
  val wrapper           = X zip Y

  stage = new JFXApp.PrimaryStage {
      title.value = "Data dashboard"
      scene = new Scene(700, 700) {
        val xAxis = NumberAxis()
        val yAxis = NumberAxis()

        val pData = XYChart.Series[Number, Number](
          "Data",
          ObservableBuffer(wrapper.map(z => XYChart.Data[Number, Number](z._1, z._2)): _*))
        val plot = new ScatterChart(xAxis, yAxis, ObservableBuffer(pData))

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

