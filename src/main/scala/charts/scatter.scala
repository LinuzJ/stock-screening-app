package charts

import data.Data
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{CategoryAxis, NumberAxis, ScatterChart, XYChart}

class scatter(data: Seq[(String, Data)]) {

  def getChart: ScatterChart[String, Number] = createScatterChart(data)

  private def createScatterChart(data: Seq[(String, Data)]): ScatterChart[String, Number] = {
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
