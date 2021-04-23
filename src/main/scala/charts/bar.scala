package charts

import data.Data
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, StackedBarChart, XYChart}

class Bar(inputData: Seq[(String, Data)]) extends Chart {
  
  private var currentData: Seq[(String, Data)] = inputData
  private val thisChart: StackedBarChart[String, Number] = initChart

  private def initChart: StackedBarChart[String, Number] = {
     if (currentData.isEmpty) { return new StackedBarChart[String, Number](new CategoryAxis(), new NumberAxis()) }

      val readyData: Seq[(String, Seq[(String, Number)])] = currentData.map(x => (x._1, x._2.getVolumeData))

      // Create and setup the axis
      val xAxis: CategoryAxis = new CategoryAxis()
      val yAxis: NumberAxis = new NumberAxis()
      xAxis.setLabel("Time Period")
      xAxis.setCategories(ObservableBuffer(currentData.head._2.getVolumeData.map(_._1)))
      yAxis.setLabel("Volume")

      // Create the chart instance
      val barChart: StackedBarChart[String, Number] = new StackedBarChart[String, Number](xAxis, yAxis)
      barChart.setTitle("Volume over specific timeperiod")
      barChart.getXAxis.setTickLabelRotation(90)

      // adding the series to the chart itself
      createSeries(currentData).foreach{
        thisSeries => {
          barChart.getData.add(thisSeries)
        }
      }

      barChart
  }
  private def updateChart(): Unit = {
    // new sequence of series with the new data
    val newSeries = createSeries(currentData)
    thisChart.getData.remove(0, thisChart.getData.size())
    newSeries.foreach{
      thisSeries => {
        thisChart.getData.add(thisSeries)
      }
    }
  }

  private def createSeries(newData: Seq[(String, Data)]): Seq[XYChart.Series[String, Number]] = {
      val readyData: Seq[(String, Seq[(String, Number)])] = newData.map(x => (x._1, x._2.getVolumeData))
      var series: Seq[XYChart.Series[String, Number]] = Seq()
      for (i <- readyData.indices) {
        series = series :+ new XYChart.Series[String, Number]()
      }

      var ZipData = series zip readyData

      for (serie <- ZipData) {
        // (XYChart.Series[String, Number], (String, Seq[(String, Int)]))
        // set the name of the series to the name of the stock
        serie._1.setName(serie._2._1)

        // add the data to the specific series
        for (chartdata <- serie._2._2) {
          var newData = new XYChart.Data[String, Number]()
          newData.setXValue(chartdata._1)
          newData.setYValue(chartdata._2)
          serie._1.getData.add(newData)
        }
      }
      series
  }
  // public mthods to access the chart
  override def update(newData: Seq[(String, Data)]): Unit = { currentData = newData; updateChart() }
  def getChart: StackedBarChart[String, Number] = thisChart
}
//class Bar(inputData: Seq[(String, Data)]) extends Chart {
//
//  def getChart: StackedBarChart[String, Number] = thisChart
//
//  // method to update the chart
//  override def update(newData: Seq[(String, Data)]): Unit = { thisChart = createChart(newData) }
//
//  private var thisChart: StackedBarChart[String, Number] = createChart(inputData)
//
//
//  private def createChart(input: Seq[(String, Data)]): StackedBarChart[String, Number] = {
//
//    if (input.isEmpty) { return new StackedBarChart[String, Number](new CategoryAxis(), new NumberAxis()) }
//
//    val readyData: Seq[(String, Seq[(String, Number)])] = input.map(x => (x._1, x._2.getVolumeData))
//
//    // Create and setup the axis
//    val xAxis: CategoryAxis = new CategoryAxis()
//    val yAxis: NumberAxis = new NumberAxis()
//    xAxis.setLabel("Time Period")
//    xAxis.setCategories(ObservableBuffer(input.head._2.getVolumeData.map(_._1)))
//    yAxis.setLabel("Volume")
//
//    // Create the chart instance
//    val barChart: StackedBarChart[String, Number] = new StackedBarChart[String, Number](xAxis, yAxis)
//    barChart.setTitle("Volume over specific timeperiod")
//    barChart.getXAxis.setTickLabelRotation(90)
//    var series: Seq[XYChart.Series[String, Number]] = Seq()
//    for (i <- readyData.indices) {
//      series = series :+ new XYChart.Series[String, Number]()
//    }
//
//    var ZipData = series zip readyData
//
//    for (serie <- ZipData) {
//      // (XYChart.Series[String, Number], (String, Seq[(String, Int)]))
//      // set the name of the series to the name of the stock
//      serie._1.setName(serie._2._1)
//
//      // add the data to the specific series
//      for (chartdata <- serie._2._2) {
//        var newData = new XYChart.Data[String, Number]()
//        newData.setXValue(chartdata._1)
//        newData.setYValue(chartdata._2)
//        serie._1.getData.add(newData)
//      }
//    }
//    // adding the series to the chart itself
//    series.foreach{
//      thisSeries => {
//        barChart.getData.add(thisSeries)
//      }
//    }
//
//    barChart
//  }
//}
