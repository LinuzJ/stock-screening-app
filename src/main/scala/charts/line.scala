package charts

import data.Data
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{CategoryAxis, LineChart, NumberAxis, ScatterChart, XYChart}

class Line(data: Seq[(String, Data)]) {

  def getChart: LineChart[String, Number] = createChart(data)

  private def createChart(data: Seq[(String, Data)]): LineChart[String, Number] = {
        val xAxis = CategoryAxis()
        val yAxis = NumberAxis()
        xAxis.setLabel("Time")
        yAxis.setLabel("Close price")

        val series = createSeries(data)
        //  adding the data to the axis
        val plot = new LineChart(xAxis, yAxis)
        series.foreach(x => plot.getData.add(x))
        plot
  }

  // helper for setting up the data itself in a chart-readable format
  private def createSeries(inputData: Seq[(String, Data)]) = {
    inputData.map{
      stockData => {
        XYChart.Series[String, Number](
        stockData._1,
        ObservableBuffer(stockData._2.getFormatted.map(i => XYChart.Data[String, Number](i._1, i._2)): _*))

      }
    }

  }
}
