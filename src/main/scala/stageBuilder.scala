import charts.{Bar, Line, Pie}
import data.{Data, DataPane}
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ComboBox, DatePicker, Label, SplitPane}
import scalafx.scene.layout.{BorderPane, HBox, VBox}

import java.time.LocalDate

class StageBuilder {
    // interval for graph (minutes) Valid intervals: [1m, 2m, 5m, 15m, 30m, 60m, 90m, 1h, 1d, 5d, 1wk, 1mo, 3mo]
  val interval = 60

  val stocks: StocksToDisplay = new StocksToDisplay
  val dates: DatesToDisplay = new DatesToDisplay

  // method for getting instances of Data for the different stocks
  def buildData(inputStocks: Seq[String]): Seq[(String, Data)] = inputStocks.map{
    x =>
    (x, new Data(x, dates.getDates("start"), dates.getDates("end"), interval))
  }
  
  var stockData: Seq[(String, Data)] = buildData(stocks.getStocks)
  
  
  // charts with initial values
  val lineChart                       = new Line(stockData)
  val pieChart                        = new Pie(stockData)
  val barChart                        = new Bar(stockData)
  val listOfCharts: Seq[charts.Chart] = List(lineChart, pieChart, barChart)

  // dataPane
  val dataPane                        = new DataPane(stockData)

  // date pickers
  var datePickerStart                  = new DatePicker(LocalDate.now.minusDays(5))
  datePickerStart.onAction = (e) => {
    dates.changeDates(datePickerStart.getValue, dates.getDates("end"))
  }
  var datePickerEnd                   = new DatePicker(LocalDate.now)
  datePickerEnd.onAction = (e) => {
    dates.changeDates(dates.getDates("start"), datePickerEnd.getValue)
  }

  val button1 = new Button("Update *TEST*")
  button1.getStyleClass.add("test")

  button1.onAction = (e) => {
    updateStage()
  }

  val buttonToSetup = new Button{
    text = "Switch scenes lol"
    onAction = (e) => changeStage(false)
  }

  val buttonToDashboard = new Button{
    text = "Switch scenes lol"
    onAction = (e) => {
      updateStage()
      changeStage(true)
    }
  }


  val controlBox: ComboBox[String] = new ComboBox[String](stocks.getStocks)
  controlBox.getSelectionModel.select(stockData.head._1)
  controlBox.onAction = (e) => {
    var dataTemp = stockData
    dataPane.changeStock(dataTemp, controlBox.getValue)
    updateStage()
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
      var splitRightBottomTop = new VBox()
      splitRightBottomTop.children.add(dataPane.getPane)
      splitRightBottomTop.children.add(controlBox)
      splitRightBottom.items.add(splitRightBottomTop)

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
      val splitRight = new SplitPane()
      val splitRightTop = new BorderPane()
      splitRightTop.setCenter(new Label(stocks.toString))
      val splitRightBottom = new BorderPane()
      splitRightBottom.setCenter(buttonToDashboard)
      splitRightBottom.setPrefSize(200, 200)
      splitRight.items.add(splitRightTop)
      splitRight.items.add(splitRightBottom)
      splitRight.orientation = scalafx.geometry.Orientation.Vertical
      splitCenter.items.add(splitRight)
      pane.setCenter(splitCenter)

      scene = new Scene(pane, 1000, 1000)
    }
  }

  // stage initially set to the SetupStage
  var stageVariable: JFXApp.PrimaryStage = SetupStage
  def changeStage(input: Boolean): Unit = { if (input) {stageVariable = StockStage} else {stageVariable = SetupStage} }
  def updateStage(): Unit = {
    stockData = buildData(stocks.getStocks)
    for (chart <- listOfCharts) {
      chart.update(stockData)
    }
    changeStage(true)
  }
}
