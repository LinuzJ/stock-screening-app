import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.{FlowPane, Pane}
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
  val X_formatted = X.map(timeFormat.format(_))

  val zipped           = X_formatted zip Y

  stage = new JFXApp.PrimaryStage {
      title.value = "Data dashboard"
      val pane = new FlowPane()
      pane.getChildren.add(createScatterChart(zipped))
      scene = new Scene(pane, 700, 700)

  }
  def createScatterChart(data: Seq[(String, Double)]): ScatterChart[String, Number] = {
        val xAxis = CategoryAxis()
        val yAxis = NumberAxis()
        xAxis.setLabel("Time")
        yAxis.setLabel("Close price")

        // setting up the data itself in a chart-readable format
        val Data = XYChart.Series[String, Number](
          "Price over time",
          ObservableBuffer(zipped.map(i => XYChart.Data[String, Number](i._1, i._2)): _*))

        //  adding the data to the axis
        val plot = new ScatterChart(xAxis, yAxis)
        plot.getData.add(Data)
        plot.setPrefSize(400, 400)
        plot
  }


//  val root = new Pane
//  val scene = new Scene(root)
//
//  stage.scene = scene
//
//
//  root.children = new Label()

}

