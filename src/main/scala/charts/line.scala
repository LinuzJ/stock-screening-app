package charts

import data.Data
import scalafx.Includes.observableList2ObservableBuffer
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{CategoryAxis, LineChart, NumberAxis, XYChart}
import scalafx.scene.control.{Button, Tooltip}
import scalafx.util.Duration

class Line(data: Seq[(String, Data)]) extends Chart {

  // Init values
  private var typeOfData: String = "absolute"
  private var currentData: Seq[(String, Data)] = data

  // method to get the Chart itself
  def getChart: LineChart[String, Number] = thisChart

  private val thisChart: LineChart[String, Number] = {
    val xAxis = CategoryAxis()
    val yAxis = NumberAxis()
    yAxis.forceZeroInRange = false
    xAxis.setLabel("Time")
    yAxis.setLabel("Price")
    val series = createSeriesAbsolute(currentData)
    //  adding the data to the axis
    val plot = new LineChart(xAxis, yAxis)
    series.foreach(x => plot.getData.add(x))
    plot.setTitle(s"Price over time (${if (typeOfData == "absolute") "Absolute" else "Relative"})")
    plot.getXAxis.setTickLabelRotation(90)
    plot
  }

  private def updateChart(typeOfChange: String): Unit = {
    var newSeries = createSeriesAbsolute(this.currentData)
    if (typeOfChange == "relative") { newSeries = createSeriesRelative(this.currentData) }
    thisChart.data.set(ObservableBuffer(newSeries))

    thisChart.setTitle(s"Price over time (${if (typeOfChange == "absolute") "Absolute" else "Relative"})")
    thisChart.getYAxis.setLabel({if (typeOfChange == "absolute") "Price USD" else "Relative Change"})
    thisChart.getData.forEach(s => {
      s.getData.forEach(i => {
        Tooltip.install(i.getNode, new Tooltip({
          if (typeOfChange == "absolute"){
            s"Price: ${s.getName}\n"+ "$" + roundDecimal(i.getYValue.doubleValue(), 2) + "\nAt: " + i.getXValue
          } else {
            s"Change: ${s.getName}\n"+ {
              val v: Double = i.getYValue.doubleValue()
              if (v > 1)  { roundDecimal((v - 1)*100, 2) } else { roundDecimal((1 - v) * - 100, 2) }
            } + "%"  + "\nAt: " + i.getXValue
          }
        }){
          showDelay = Duration.Zero
          showDuration = Duration.Indefinite
        })
      })
    })
    thisChart.autosize()
  }

  // helper for setting up the data itself in a chart-readable format
  private def createSeriesAbsolute(inputData: Seq[(String, Data)]) = {
    inputData.map{
      stockData => {
        XYChart.Series[String, Number](
        stockData._1,
        ObservableBuffer(stockData._2.getFormatted.map(i => XYChart.Data[String, Number](i._1, i._2)): _*))

      }
    }
  }
  private def createSeriesRelative(inputData: Seq[(String, Data)]) = {
    inputData.map(x => x).map{
      stockData => {
        XYChart.Series[String, Number](
        stockData._1,
        ObservableBuffer(stockData._2.getFormattedRelative.map(i => XYChart.Data[String, Number](i._1, i._2)): _*))

      }
    }
  }
  // method to update the chart
  override def update(newData: Seq[(String, Data)]): Unit = { currentData = newData; updateChart(typeOfData) }
  def changeTypeOfData(): Unit = {
    typeOfData = {if (typeOfData != "absolute") "absolute" else "relative"}
    updateChart(typeOfData)
  }

  val changeTypeOfDataButton: Button = new Button{
    text = s"Change to ${if (typeOfData != "absolute") "Absolute" else "Relative"} view"
    onAction = (e) => {
      changeTypeOfData()
      text = s"Change to ${if (typeOfData != "absolute") "Absolute" else "Relative"} view"
      update(currentData)
    }

  }
}
