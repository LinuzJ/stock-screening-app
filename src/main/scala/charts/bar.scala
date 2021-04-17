package charts

import data.Data
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, StackedBarChart, XYChart}

class Bar(inputData: Seq[(String, Data)]) extends Chart {

  private var barAmount: Int = 6

  def changebarAmount(changeTo: Int) = { barAmount = changeTo }
  def getChart: StackedBarChart[String, Number] = thisChart

  // method to update the chart
  override def update(newData: Seq[(String, Data)]): Unit = { thisChart = createChart(newData, barAmount) }

  private var thisChart: StackedBarChart[String, Number] = createChart(inputData, barAmount)


  private def createChart(input: Seq[(String, Data)], bars: Int): StackedBarChart[String, Number] = {

    val timeperiods: Seq[String] = getTimePeriods(input.head, bars)

    // Create and setup the axis
    val xAxis: CategoryAxis = new CategoryAxis()
    val yAxis: NumberAxis = new NumberAxis()
    xAxis.setLabel("Time Period")
    xAxis.setCategories(ObservableBuffer(timeperiods))
    yAxis.setLabel("Volume")

    // Create the chart instance
    val barChart: StackedBarChart[String, Number] = new StackedBarChart[String, Number](xAxis, yAxis)
    barChart.setTitle("Volume over specific timeperiod")

    var series: Seq[XYChart.Series[String, Number]] = Seq()
    for (i <- 0 to barAmount) {
      series = series :+ new XYChart.Series[String, Number]()
    }

    var createdData = createData(input, bars)
    var ZipData: Seq[(XYChart.Series[String, Number], (String, Seq[(Int, Int)]))] = series zip createdData

    for (serie <- ZipData) {
      // (XYChart.Series[String, Number], (String, Seq[(Int, Int)]))

      // set the name of the series to the name of the stock
      serie._1.setName(serie._2._1)

      // add the data to the specific series
      for (chartdata <- serie._2._2) {
        var newData = new XYChart.Data[String, Number]()
        newData.setXValue(timeperiods(chartdata._1))
        newData.setYValue(chartdata._2)
        serie._1.getData.add(newData)
      }
    }
    // adding the series to the chart itself
    series.foreach{
      thisSeries => {
        barChart.getData.add(thisSeries)
      }
    }

    barChart
  }

  //  helper for handling the data for the graph and setting it up ready for the chart
  private def createData(input: Seq[(String, Data)], amountOfBars: Int): Seq[(String, Seq[(Int, Int)])] = {

      var output : Seq[(String, Seq[(Int, Int)])] = Seq()
      for (stockData <- input) {
        var stockName = stockData._1
        var zippedPeriods: Seq[(Int, Int)] = List.range(0, amountOfBars) zip getVolumePeriods(stockData, amountOfBars)
        output = output :+ (stockName, zippedPeriods)
      }
      output
  }

  private def getTimePeriods(input: (String, Data), barAmount: Int): Seq[String] = {
    val timeData: Seq[String] = input._2.getVolumeData.map(_._1)
    val groupSize: Int = timeData.length / barAmount
    val grouped: Seq[Seq[String]] = timeData.grouped(groupSize).toList
    grouped.map{
      subList => {
        subList.head + " - " + subList.last
      }
    }
  }
  private def getVolumePeriods(input: (String, Data), barAmount: Int): Seq[Int] = {
    val timeData: Seq[Double] = input._2.getVolumeData.map(_._2)
    val grouped: Seq[Seq[Double]] = timeData.grouped(barAmount).toList
    grouped.map(x => x.sum.toInt)
  }

}


