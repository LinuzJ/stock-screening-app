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
  val time: Int = 2
  val unixTime: Long = Instant.now.getEpochSecond - (time * 24*60*60)
  val timeFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss")

  // interval for graph (minutes)
  val interval = 5

  // here you choose which stocks to monitor
  val stocks: Seq[String] = List("AAPL", "NOK", "BABA")
  // The fetched data is stored here
  val data: Seq[(String, Data)] = stocks.map(x => (x, new Data(s"https://query1.finance.yahoo.com/v8/finance/chart/${x}?symbol=${x}&period1=${unixTime}&period2=9999999999&interval=${interval}m")))

  stage = new JFXApp.PrimaryStage {
      title.value = "Data dashboard"
      val pane = new FlowPane()

      pane.getChildren.add(createScatterChart(data))
      pane.getChildren.add(new Label("Hello"))
      scene = new Scene(pane, 700, 700)

  }
  def createScatterChart(data: Seq[(String, Data)]): ScatterChart[String, Number] = {
        val xAxis = CategoryAxis()
        val yAxis = NumberAxis()
        xAxis.setLabel("Time")
        yAxis.setLabel("Close price")

        val series = createSeries(data)
        //  adding the data to the axis
        val plot = new ScatterChart(xAxis, yAxis)
        series.foreach(x => plot.getData.add(x))
        plot.setPrefSize(500, 500)
        plot
  }
  // setting up the data itself in a chart-readable format
  def createSeries(inputData: Seq[(String, Data)]) = {
    inputData.map{
      stockData => {
        XYChart.Series[String, Number](
        stockData._1,
        ObservableBuffer(stockData._2.getFormatted.map(i => XYChart.Data[String, Number](i._1, i._2)): _*))
      }
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

