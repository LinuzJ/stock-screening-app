import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.{Button, DatePicker, Label, SplitPane}
import scalafx.scene.layout.{BorderPane, GridPane, HBox, Pane, VBox}
import data.Data
import data.DataPane
import charts.Line
import charts.Pie
import charts.Bar
import scalafx.scene.input.KeyCode.V

import java.text.SimpleDateFormat
import java.time.{Instant, LocalDate}
import java.util.Date

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

  // interval for graph (minutes) Valid intervals: [1m, 2m, 5m, 15m, 30m, 60m, 90m, 1h, 1d, 5d, 1wk, 1mo, 3mo]
  val interval = 60


  // here you choose which stocks to monitor
  var stocks: Seq[String] = List("AAPL", "GME", "TSLA")

  // start and end date
  var dates: Map[String, LocalDate] = Map("start" -> LocalDate.now.minusDays(10), "end" -> LocalDate.now)

  def changeDates(start: LocalDate, end: LocalDate): Unit = {
    dates = Map("start" -> start, "end" -> end)
  }

  // helper method for changing the stocks
  def changeStocks(input: Seq[String]): Unit = { stocks = input }

  // method for getting instances of Data for the different stocks
  def buildData(inputStocks: Seq[String]): Seq[(String, Data)] = inputStocks.map{
    x =>
    (x, new Data(x, dates("start"), dates("end"), interval))
  }

  // charts
  val lineChart                       = new Line(buildData(stocks))
  val pieChart                        = new Pie(buildData(stocks))
  val barChart                        = new Bar(buildData(stocks))
  val listOfCharts: Seq[charts.Chart] = List(lineChart, pieChart, barChart)

  // dataPane
  val dataPane                        = new DataPane(buildData(stocks))

  // date pickers
  var datePickerStart                  = new DatePicker(LocalDate.now.minusDays(5))
  datePickerStart.onAction = (e) => {
    changeDates(datePickerStart.getValue, dates("end"))
  }
  var datePickerEnd                   = new DatePicker(LocalDate.now)
  datePickerEnd.onAction = (e) => {
    changeDates(dates("start"), datePickerEnd.getValue)
  }

  val button1 = new Button("Update *TEST*")
  button1.getStyleClass.add("test")

  button1.onAction = (e) => {
    // FOT THE SAKE OF THE TEST CHANGE THE STOCKS TO THIS
    for (chart <- listOfCharts) {
      chart.update(buildData(stocks))
    }
    updateStage(true)
  }

  val buttonToSetup = new Button{
    text = "Switch scenes lol"
    onAction = (e) => updateStage(false)
  }

  val buttonToDashboard = new Button{
    text = "Switch scenes lol"
    onAction = (e) => updateStage(true)
  }



  def StockStage: JFXApp.PrimaryStage = {
    new JFXApp.PrimaryStage {

      title.value = "Data dashboard"
      val pane = new BorderPane()

      ////////////////////////////////
      val splitLeft = new SplitPane()
      splitLeft.items.add{
        val temp = new BorderPane()
        temp.setCenter(lineChart.getPane)
        temp
      }
      splitLeft.items.add{
        val temp = new BorderPane()
        temp.setCenter(barChart.getPane)
        temp
      }
      splitLeft.orientation = scalafx.geometry.Orientation.Vertical
      ////////////////////////////////

      ////////////////////////////////
      val splitRight = new SplitPane()
      //--------------------------------
      val splitRigthTop = new BorderPane()
      splitRigthTop.children.add(pieChart.getPane)
      //--------------------------------
      val splitRightBottom = new SplitPane()
      splitRightBottom.items.add(dataPane.getPane)
      splitRightBottom.items.add{
        val temp = new BorderPane()
        val tempSplit = new HBox()
        tempSplit.children.add(datePickerStart)
        tempSplit.children.add(datePickerEnd)
        temp.setTop(tempSplit)
        temp.setCenter(buttonToSetup)
        temp.setBottom(button1)
        temp
      }
      splitRightBottom.orientation = scalafx.geometry.Orientation.Vertical
      //--------------------------------
      splitRight.items.add(splitRigthTop)
      splitRight.items.add(splitRightBottom)
      splitRight.orientation = scalafx.geometry.Orientation.Vertical
      ////////////////////////////////

      ////////////////////////////////
      val splitCenter = new SplitPane()
      splitCenter.items.add(splitLeft)
      splitCenter.items.add(splitRight)
      splitCenter.orientation = scalafx.geometry.Orientation.Horizontal
      ////////////////////////////////

      pane.setCenter(splitCenter)
      scene = new Scene(pane, 1200, 1000) {
        stylesheets = List(getClass.getResource("style.css").toExternalForm)
      }

    }
  }
  def SetupStage: JFXApp.PrimaryStage = {
    new JFXApp.PrimaryStage {
      title.value = "Setup"
      val pane = new BorderPane()
      val splitCenter = new SplitPane()
      splitCenter.items.add(new Label("Test"))
      splitCenter.items.add(buttonToDashboard)
      pane.setCenter(splitCenter)

      scene = new Scene(pane, 1000, 1000)
    }
  }

  // stage initially set to the SetupStage
  var stageVariable: JFXApp.PrimaryStage = SetupStage
  def updateStage(input: Boolean): Unit = { if (input) {stageVariable = StockStage} else {stageVariable = SetupStage} }

  stage = stageVariable
}

