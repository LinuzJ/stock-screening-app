import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, SplitPane}
import scalafx.scene.layout.{BorderPane, GridPane, Pane}
import data.Data
import charts.Line
import charts.Pie
import charts.Bar

import java.text.SimpleDateFormat
import java.time.Instant

object Main extends JFXApp {

  // TODO
  // - Test for the fetching/name of stock while creating the data variable
  // - PieChart
  //    - interactivity
  // - Bar chart
  //    - make it actually work, and interactivity!!!
  // - Data block
  // - Control Panel
  // - checkboxes for which data to show in which chart, aka a pane under the charts with relevant buttons
  // - reload data

  // timeframe for graph
  val time: Int = 10
  // interval for graph (minutes) Valid intervals: [1m, 2m, 5m, 15m, 30m, 60m, 90m, 1h, 1d, 5d, 1wk, 1mo, 3mo]
  val interval = 30


  // here you choose which stocks to monitor
  var stocks: Seq[String] = List("AAPL", "GME", "TSLA")



  // helper method for changing the stocks
  def changeStocks(input: Seq[String]): Unit = { stocks = input }

  // The fetched data is stored here
  val unixTime: Long = Instant.now.getEpochSecond - (time * 24*60*60)
  // method for getting instances of Data for the different stocks
  def buildData(inputStocks: Seq[String]): Seq[(String, Data)] = inputStocks.map{
    x =>
    (x, new Data(s"https://query1.finance.yahoo.com/v8/finance/chart/${x}?symbol=${x}&period1=${this.unixTime}&period2=9999999999&interval=${this.interval}m"))
  }

  // charts
  val lineChart     = new Line(buildData(stocks))
  val pieChart      = new Pie(buildData(stocks))
  val barChart      = new Bar(buildData(stocks))
  val listOfCharts: Seq[charts.Chart] = List(lineChart, pieChart, barChart)


  val button = new Button("Update *TEST*")

  button.onAction = (e) => {
    // FOT THE SAKE OF THE TEST CHANGE THE STOCKS TO THIS
    changeStocks(List("AAPL", "GME", "NOK"))
    for (chart <- listOfCharts) {
      chart.update(buildData(stocks))
    }
    updateStage()
  }


  def newStage: JFXApp.PrimaryStage = {
    new JFXApp.PrimaryStage {

      title.value = "Data dashboard"
      val pane = new BorderPane()
      val splitLeft = new SplitPane()
      splitLeft.items.add(lineChart.getPane)
      splitLeft.items.add(barChart.getPane)
      splitLeft.orientation = scalafx.geometry.Orientation.Vertical
      val splitRight = new SplitPane()
      splitRight.items.add(pieChart.getPane)
      splitRight.items.add(button)
      splitRight.orientation = scalafx.geometry.Orientation.Vertical

      val splitCenter = new SplitPane()
      splitCenter.items.add(splitLeft)
      splitCenter.items.add(splitRight)
      splitCenter.orientation = scalafx.geometry.Orientation.Horizontal

      pane.setCenter(splitCenter)
//      pane.add(lineChart.getPane, 0, 0)
//      pane.add(pieChart.getPane, 1, 0)
//      pane.add(barChart.getPane, 0, 1)
//      pane.add(button, 1, 1)
      scene = new Scene(pane, 1000, 1000)

    }
  }

  var stageVariable: JFXApp.PrimaryStage = newStage
  def updateStage(): Unit = { stageVariable = newStage }

  stage = stageVariable


}

