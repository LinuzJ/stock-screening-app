package charts

import data.Data
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{CategoryAxis, LineChart, NumberAxis, ScatterChart, XYChart}
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, VBox}

class Line(data: Seq[(String, Data)]) extends Chart {

  // Init values
  private var typeOfData: String = "absolute"
  private var currentData: Seq[(String, Data)] = data

  // method to get the pieChart
  def getChart: LineChart[String, Number] = thisChart

  private val thisChart: LineChart[String, Number] = {
    val xAxis = CategoryAxis()
    val yAxis = NumberAxis()
    xAxis.setLabel("Time")
    yAxis.setLabel("Price")
    val series = createSeriesAbsolute(currentData)
    //  adding the data to the axis
    val plot = new LineChart(xAxis, yAxis)
    series.foreach(x => plot.getData.add(x))
    plot.setTitle(s"Price over time (${if (typeOfData == "absolute") "Absolute" else "Relative"})")
    plot
  }

  private def updateChart(typeOfChange: String): Unit = {
    var newSeries = createSeriesAbsolute(this.currentData)
    if (typeOfChange == "relative") { newSeries = createSeriesRelative(this.currentData) }
    thisChart.getData.remove(0, thisChart.getData.size)
    newSeries.foreach(x => {
      thisChart.getData.add(x)
    })
    thisChart.setTitle(s"Price over time (${if (typeOfData == "absolute") "Absolute" else "Relative"})")
  }

  // helper for setting up the data itself in a chart-readable format
  private def createSeriesAbsolute(inputData: Seq[(String, Data)]) = {
    inputData.map{
      stockData => {
        println((stockData._1, stockData._2))
        XYChart.Series[String, Number](
        stockData._1,
        ObservableBuffer(stockData._2.getFormatted.map(i => XYChart.Data[String, Number](i._1, i._2)): _*))

      }
    }
  }
  private def createSeriesRelative(inputData: Seq[(String, Data)]) = {
    inputData.map(x => x).map{
      stockData => {
        println((stockData._1, stockData._2))
        XYChart.Series[String, Number](
        stockData._1,
        ObservableBuffer(stockData._2.getFormattedRelative.map(i => XYChart.Data[String, Number](i._1, i._2)): _*))

      }
    }
  }
  // method to update the chart
  override def update(newData: Seq[(String, Data)]): Unit = { currentData = newData; updateChart(typeOfData) }
  private def changeTypeOfData(): Unit = {
    typeOfData = {if (typeOfData != "absolute") "absolute" else "relative"}
    updateChart(typeOfData)
  }

  val changeTypeOfDataButton: Button = new Button{
    text = s"Change to ${if (typeOfData != "absolute") "Absolute" else "Relative"} view"
    onAction = (e) => {
      changeTypeOfData()
      text = s"Change to ${if (typeOfData != "absolute") "Absolute" else "Relative"} view"
    }

  }
}
