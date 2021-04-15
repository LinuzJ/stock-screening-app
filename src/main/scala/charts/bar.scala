package charts

import data.Data
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, StackedBarChart, XYChart}

class Bar(inputData: Seq[(String, Data)]) {

  private var barAmount: Int = 6

  def changebarAmount(changeTo: Int) = barAmount = changeTo

  def getChart: StackedBarChart[String, Number] = createChart(inputData, barAmount)

  private def createChart(input: Seq[(String, Data)], bars: Int): StackedBarChart[String, Number] = {
    val timeperiods: Seq[String] = getTimePeriods(input.head, bars)

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

//    var timeZipData = timeperiods zip createData(input, bars)

    for (series <- series zip(timeperiods zip createData(input, bars))) {
      // set the name of the series to the name of the stock
      series._1.setName(series._2._1)

      // add the data to the specific series
      for (chartdata <- series._2._2._2) {
        var newData = new XYChart.Data[String, Number]()
        newData.setXValue(chartdata._1)
        newData.setYValue(chartdata._2)
        series._1.getData.add(newData)
      }
    }
    series.foreach{
      thisSeries => {
        barChart.getData.add(thisSeries)
      }
    }

    barChart
  }

  private def createData(input: Seq[(String, Data)], amountOfBars: Int): Seq[(String, Seq[(String, Int)])] = {

      var output : Seq[(String, Seq[(String, Int)])] = Seq()
      for (stockData <- input) {
        var stockName = stockData._1
        var zippedPeriods: Seq[(String, Int)] = getTimePeriods(stockData, amountOfBars) zip getVolumePeriods(stockData, amountOfBars)
        output :+ (stockName, zippedPeriods)
      }
      output
  }

  private def getTimePeriods(input: (String, Data), barAmount: Int): Seq[String] = {
    val timeData: Seq[String] = input._2.getFormatted.map(_._1)
    val grouped: Seq[Seq[String]] = timeData.grouped(barAmount).toList
    grouped.map{
      subList => {
        subList.head + " - " + subList.last
      }
    }
  }
  private def getVolumePeriods(input: (String, Data), barAmount: Int): Seq[Int] = {
    val timeData: Seq[Double] = input._2.getFormatted.map(_._2)
    val grouped: Seq[Seq[Double]] = timeData.grouped(barAmount).toList
    grouped.map(x => x.sum.toInt)
  }

}


