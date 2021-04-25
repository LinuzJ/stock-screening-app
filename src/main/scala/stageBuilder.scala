import charts.Charts
import components._
import data.{Data, TimeData}
import scalafx.Includes._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}
import scalafx.scene.layout._

import java.time.LocalDate

class StageBuilder(tickers: Seq[(String, String)]) extends Layouts {

  val stocks: StocksToDisplay = new StocksToDisplay
  val dates: TimeData = new TimeData

  // method for getting instances of Data for the different stocks
  def buildData(inputStocks: Seq[String]): Option[Seq[(String, Data)]] = {
    val m = stocks.getStocks
    try {
      Some(inputStocks.map{
      x =>
      (x, Data.generate(x, dates.getDates("start"), dates.getDates("end"), dates.getInterval))
      })
    } catch {
      case e: Throwable => ErrorPopup.getPopup("Error!", "There was an error while getting the data, please check your internet connection!", e.getMessage, stageVariable); Some(Seq[(String, Data)]())
    }

  }
  
  var stockData: Seq[(String, Data)] = buildData(stocks.getStocks).get
  
  
  // charts with initial values
  val lineChart                       = Charts.line(stockData)
  val pieChart                        = Charts.pie(stockData)
  val barChart                        = Charts.bar(stockData)
  val listOfCharts: Seq[charts.Chart] = List(lineChart, pieChart, barChart)
  val dataPane                        = new DataPane(stockData, tickers)
  val listView: ListView[String]      = new ListView(tickers.map(_._1))

  // date pickers
  var datePickerStart                 = new DatePicker(LocalDate.now.minusDays(5))
  var datePickerEnd                   = new DatePicker(LocalDate.now)

  // Buttons
  val updateButton                    = new Button("Refresh")
  val buttonToSetup                   = new Button{ text = "Change stocks" ;            onAction = (e) => changeStage(false) }
  val buttonToDashboard               = new Button{ text = "Continue to the dashboard"; onAction = (e) => { updateStage(); changeStage(true) } }
  val buttonExit                      = new Button{ text = "Exit";                      onAction = (e) => { Platform.exit() } }
  val buttonAdd                       = new Button("ADD")
  // Styling for the buttons
  updateButton.getStyleClass.add("controlPanelButton")
  buttonToSetup.getStyleClass.add("controlPanelButton")
  buttonToDashboard.getStyleClass.add("controlPanelButton")
  buttonExit.getStyleClass.add("controlPanelButton")
  buttonAdd.getStyleClass.add("controlPanelButton")
  listView.getStyleClass.add("list-view")

  // Control boxes
  var controlBoxStock: ComboBox[String]     = new ComboBox[String](stockData.map(_._1))
  val controlBoxInterval: ComboBox[String]  = new ComboBox[String](List("1m", "2m", "5m", "15m", "30m", "60m", "90m", "1d", "5d",  "1wk", "1mo"))


  // Action handling for the buttons/other interactive objects

  datePickerStart.onAction = (e) => {
    dates.changeDates(datePickerStart.getValue, dates.getDates("end"))
    updateStage()
  }
  datePickerEnd.onAction = (e) => {
    dates.changeDates(dates.getDates("start"), datePickerEnd.getValue)
    updateStage()
  }

  updateButton.onAction = (e) => {
    updateStage()
  }
  buttonAdd.onAction = (e) => {
    val tick = listView.selectionModel().getSelectedItem
    val tickersMap = tickers.toMap
    try {
      if (!stocks.getStocks.contains(tickersMap(tick))) {
        stocks.changeStocks(stocks.getStocks :+ (tickersMap(tick)))
        changeStage(false)
      } else {
        ErrorPopup.getPopup("Error", "Error while adding ticker!", "This ticker has already been added, please choose another one.", stageVariable)
      }
    } catch {
      case e: Throwable => ErrorPopup.getPopup("Error", "Reminder!", "You have to click on the company name that you want to add before clicking add! HINT: You can also press the button A to add a ticker! ", stageVariable)
    }
  }
  controlBoxStock.getSelectionModel.select({ try { stockData.head._1 } catch { case e: Throwable => "" } })
  controlBoxStock.onAction = (e) => {
    dataPane.changeStock(stockData, controlBoxStock.getValue)
    updateStage()
  }
  controlBoxInterval.getSelectionModel.select(dates.getInterval)
  controlBoxInterval.onAction = (e) => {
    try {
      dates.changeInterval(controlBoxInterval.getValue)
    } catch {
      case e: RuntimeException => {
        ErrorPopup.getPopup("Error", "Error while changing Interval!", e.getMessage, stageVariable)
      }
    }
    updateStage()

  }


  // Setting up the theme and menubar
  var theme: Theme = Theme.newT
  val menuBarInstance: MenuBarTheme.type     = MenuBarTheme
  val settingsBar = menuBarInstance.get(theme)
  menuBarInstance.thisMenu.menuOfIntrest.get.items.forEach( i => i.onAction = (e) => { theme.changeTheme(i.getText) ; changeStage(!isSetup) })


  // r = RIGHT; l = LEFT; t = TOP; b = BOTTOM
  def StockStage: JFXApp.PrimaryStage = {

    isSetup = false

    new JFXApp.PrimaryStage {

      title.value = "Data dashboard"

      // generate the main pane for the dashboard
      val pane = new BorderPane(){
        top = settingsBar
        center = renderDataDashboard(
          generateDDLeft(
            lineChart.getPane,
            barChart.getPane
          ),
          generateDDRight(
            pieChart.getPane,
            dataPanel(dataPane.getPane, controlBoxStock, controlBoxInterval),
            controlPanel(dateGrid(datePickerStart, datePickerEnd), buttonGrid(lineChart.changeTypeOfDataButton, updateButton, buttonToSetup, buttonExit))
          )
        )
      }

      pane.children.forEach(x => x.getStyleClass.add("theme"))

      scene = new Scene(pane, 1200, 1000) {
        stylesheets = List(getClass.getResource(s"${try {theme.get} catch { case e: Throwable => "Green"}}.css").toExternalForm)
        onKeyPressed = { e => {
          val combo = new KeyCodeCombination(KeyCode.Tab, KeyCombination.ControlDown)
          if (combo.`match`(e)) {
            if (isSetup) { changeStage(true) } else { changeStage(false) }
            }
          }
        }
      }
    }
  }


  def SetupStage: JFXApp.PrimaryStage = {

    isSetup = true

    new JFXApp.PrimaryStage {

      title.value = "Setup"

      val rightTop = renderFlow
      stockData.foreach{
        stock => {
          val boxObject = TickerDisplayBox.get(stock._1)
          boxObject.button.onMouseClicked = (e) => {
            stocks.changeStocks{
              stockData.filter(x => x._1 != boxObject.label.getText).map(_._1)
            }
            changeStage(false)
          }
          rightTop.children += boxObject.getBox
        }
      }


      val pane = new BorderPane(){
        top     = settingsBar
        center  = renderSetup(
                    listView,
                    new BorderPane(){
                      top = rightTop
                      bottom = buttonGrid(buttonToDashboard, buttonExit, buttonAdd)
                  })
      }

      pane.children.forEach( x => x.getStyleClass.add("theme") )

      scene = new Scene(pane, 1000, 1000) {
        stylesheets = List(getClass.getResource(s"${try {theme.get} catch { case e: Throwable => "Green"}}.css").toExternalForm)
        onKeyPressed = {
          e => {
              val combo = new KeyCodeCombination(KeyCode.Tab, KeyCombination.ControlDown)
              if (combo.`match`(e)) {
                if (isSetup) {
                  updateStage()
                  changeStage(true) } else { changeStage(false) }
              } else if (e.getCode == KeyCode.A.delegate) {
                val tick = listView.selectionModel().getSelectedItem
                val tickersMap = tickers.toMap
                try {
                  if (!stocks.getStocks.contains(tickersMap(tick))) {
                    stocks.changeStocks(stocks.getStocks :+ (tickersMap(tick)))
                    changeStage(false)
                  } else {
                    ErrorPopup.getPopup("Error", "Error while adding ticker!", "This ticker has already been added, please choose another one.", stageVariable)
                  }
                } catch {
                  case e: Throwable => ErrorPopup.getPopup("Error", "Reminder!", "You have to click on the company name that you want to add before clicking add! HINT: You can also press the button A to add a ticker! ", stageVariable)
                }
              }
          }
        }
      }
    }
  }

  // stage initially set to the SetupStage
  var stageVariable: JFXApp.PrimaryStage = SetupStage
  var isSetup: Boolean = true

  def changeStage(input: Boolean): Unit = {
    if (input) {
      stageVariable = StockStage
      dataPane.update(stockData)
    } else {
      stockData = buildData(stocks.getStocks).get
      stageVariable = SetupStage
    }
  }

  def updateStage(): Unit = {
    stockData = buildData(stocks.getStocks).get
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
object StageBuilder {
  def newStage(i: Seq[(String, String)]) = { new StageBuilder(i) }
}
