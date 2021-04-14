package charts

import data.Data
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, StackedBarChart, XYChart}

class Bar(inputData: Seq[(String, Data)]) {

  private var barAmount: Int = 6

  def changebarAmount(changeTo: Int) = barAmount = changeTo

  def getChart: BarChart[String, Number] = createChart(inputData)

  private def createChart(input: Seq[(String, Data)]): BarChart[String, Number] = {
    val timeperiods: Seq[String] = getTimePeriods(input, this.barAmount)

    val xAxis: CategoryAxis = new CategoryAxis()
    val yAxis: NumberAxis = new NumberAxis()
    xAxis.setLabel("Time Period")
    xAxis.setCategories(ObservableBuffer(timeperiods))
    yAxis.setLabel("Volume")

    val barChart: StackedBarChart[String, Number] = new StackedBarChart[String, Number](xAxis, yAxis)

    var series: Seq[XYChart.Series[String, Number]] = Seq()
    for (i <- 0 to barAmount) {
      series :+ new XYChart.Series[String, Number]()
    }

  }

  private def createData(input: Seq[(String, Data)], amountOfBars: Int): Seq[(String, XYChart.Data[String, Number])] = {

      var output : Seq[(String, XYChart.Data[String, Number])] = Seq()
      for (stockData <- input) {
        stockData
      }
  }

  private def getTimePeriods(input: Seq[(String, Data)], barAmount: Int): Seq[String] = {
    val timeData: Seq[String] = input.head._2.getFormatted.map(_._1)
    val grouped: Seq[Seq[String]] = timeData.grouped(barAmount).toList
    grouped.map{
      subList => {
        subList.head + " - " + subList.last
      }
    }
  }

}


