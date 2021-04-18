import data.readTickers
import scalafx.application.JFXApp


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

  val tickers = readTickers.fromFile("tickers.txt")

  val buildStage = new StageBuilder(tickers)
  stage = buildStage.stageVariable

}

