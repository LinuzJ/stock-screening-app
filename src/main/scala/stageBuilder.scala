import charts.{Bar, Line, Pie}
import components.{ControlBox, DataPane, StocksToDisplay, TickerDisplayBox, tickerListPane}
import data.{Data, TimeData}
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.{Alert, Button, ComboBox, DatePicker, Label, SplitPane}
import scalafx.scene.layout.{BorderPane, FlowPane, HBox, VBox}
import scalafx.Includes._
import scalafx.scene.control.Alert.AlertType

import java.time.LocalDate

class StageBuilder(tickers: Seq[(String, String)]) {



  val stocks: StocksToDisplay = new StocksToDisplay
  val dates: TimeData = new TimeData

  // method for getting instances of Data for the different stocks
  def buildData(inputStocks: Seq[String]): Seq[(String, Data)] = inputStocks.map{
    x =>
    (x, new Data(x, dates.getDates("start"), dates.getDates("end"), dates.getInterval))
  }
  
  var stockData: Seq[(String, Data)] = buildData(stocks.getStocks)
  
  
  // charts with initial values
  val lineChart                       = new Line(stockData)
  val pieChart                        = new Pie(stockData)
  val barChart                        = new Bar(stockData)
  val listOfCharts: Seq[charts.Chart] = List(lineChart, pieChart, barChart)

  // other Panes
  val dataPane                        = new DataPane(stockData)

  // date pickers
  var datePickerStart                 = new DatePicker(LocalDate.now.minusDays(5))
  datePickerStart.onAction = (e) => {
    dates.changeDates(datePickerStart.getValue, dates.getDates("end"))
    updateStage()
  }
  var datePickerEnd                   = new DatePicker(LocalDate.now)
  datePickerEnd.onAction = (e) => {
    dates.changeDates(dates.getDates("start"), datePickerEnd.getValue)
    updateStage()
  }

  
  val updateButton = new Button("Update *TEST*")
  updateButton.getStyleClass.add("test")

  updateButton.onAction = (e) => {
    updateStage()
  }

  val buttonToSetup = new Button{
    text = "Change stocks"
    onAction = (e) => changeStage(false)
  }

  val buttonToDashboard = new Button{
    text = "Continue to the dashboard"
    onAction = (e) => {
      updateStage()
      changeStage(true)
    }
  }


  var controlBoxStock: ComboBox[String] = new ComboBox[String](stockData.map(_._1))
  controlBoxStock.getSelectionModel.select({ try { stockData.head._1 } catch { case e: Throwable => "" } })
  controlBoxStock.onAction = (e) => {
    dataPane.changeStock(stockData, controlBoxStock.getValue)
    updateStage()
  }
  val controlBoxInterval: ComboBox[String] = new ComboBox[String](List("1m", "2m", "5m", "15m", "30m", "60m", "90m", "1d", "5d",  "1wk", "1mo"))
  controlBoxInterval.getSelectionModel.select(dates.getInterval)
  controlBoxInterval.onAction = (e) => {
    try {
      dates.changeInterval(controlBoxInterval.getValue)
    } catch {
      case e: RuntimeException => {
        new Alert(AlertType.Error) {
          initOwner(stageVariable)
          title = "Error"
          headerText = "Error while changing Interval!"
          contentText = e.getMessage
        }.showAndWait()
      }
    }
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
      splitRigthTop.setCenter(pieChart.getPane)
      //--------------------------------
      val splitRightBottom = new SplitPane()
      var splitRightBottomTop = new VBox()
      splitRightBottomTop.children.add(dataPane.getPane)
      splitRightBottomTop.children.add(controlBoxStock)
      splitRightBottomTop.children.add(controlBoxInterval)
      splitRightBottom.items.add(splitRightBottomTop)

      splitRightBottom.items.add{
        val temp = new BorderPane()
        val tempSplit = new HBox()
        tempSplit.children.add(datePickerStart)
        tempSplit.children.add(datePickerEnd)
        temp.setTop(tempSplit)
        temp.setCenter(buttonToSetup)
        temp.setLeft(lineChart.changeTypeOfDataButton)
        temp.setBottom(updateButton)
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
      splitCenter.dividerPositions = 0.6
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

      // MainSplit
      ///////////////////////////
      val splitCenter = new SplitPane()
      ///////////////////////////


      // Left Side of the Split
      ///////////////////////////
      val listWithTicker = new tickerListPane(tickers)
      listWithTicker.button.onAction = (e) => {
        val tick = listWithTicker.listView.selectionModel().getSelectedItem
        val tickersMap = tickers.toMap
        if (!stocks.getStocks.contains(tickersMap(tick))) {
          stocks.changeStocks(stocks.getStocks :+ (tickersMap(tick)))
          changeStage(false)
        } else println("ticker already in the liost lol idiot")
      }

      splitCenter.items.add(listWithTicker.mainPane)
      ///////////////////////////


      // Right Side of the Split
      ///////////////////////////
      val splitRight = new SplitPane()

      val splitRightTop = new BorderPane()
      val splitRightTopInside = new FlowPane()
      splitRightTopInside.getStyleClass.add("splitRightTopInside")
      stockData.foreach{
        stock => {
          val boxObject = new TickerDisplayBox(stock._1)
          boxObject.button.onAction = (e) => {
            stocks.changeStocks{
              stockData.filter(x => x._1 != boxObject.label.getText).map(_._1)
            }
            changeStage(false)
          }
          splitRightTopInside.children += boxObject.getBox
        }
      }
      splitRightTop.setCenter(splitRightTopInside)

      val splitRightBottom = new BorderPane()
      splitRightBottom.setCenter(buttonToDashboard)
      splitRightBottom.setPrefSize(200, 200)
      splitRight.items.add(splitRightTop)
      splitRight.items.add(splitRightBottom)
      splitRight.orientation = scalafx.geometry.Orientation.Vertical
      ///////////////////////////

      ///////////////////////////
      splitCenter.items.add(splitRight)
      ///////////////////////////

      pane.setCenter(splitCenter)

      scene = new Scene(pane, 1000, 1000) {
        stylesheets = List(getClass.getResource("style.css").toExternalForm)
      }
    }
  }

  // stage initially set to the SetupStage
  var stageVariable: JFXApp.PrimaryStage = SetupStage

  def changeStage(input: Boolean): Unit = {
    if (input) {
      stageVariable = StockStage
      dataPane.update(stockData)
    } else {
      stockData = buildData(stocks.getStocks)
      stageVariable = SetupStage
    }
  }

  def updateStage(): Unit = {
    stockData = buildData(stocks.getStocks)

    controlBoxStock = new ComboBox[String](stockData.map(_._1))
    controlBoxStock.getSelectionModel.select({ try { controlBoxStock.getValue } catch { case e: Throwable => "" } })
    controlBoxStock.onAction = (e) => {
    dataPane.changeStock(stockData, controlBoxStock.getValue)
    updateStage()
    }


    for (chart <- listOfCharts) {
      chart.update(stockData)
    }
    changeStage(true)
  }
}
