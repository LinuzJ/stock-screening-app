import data.readTickers
import scalafx.application.JFXApp


object Main extends JFXApp {

  // TODO
  // - lineChart adjustability between absolute and relative data
  // - Error handling for the data fetching
  // - Control Panel
  // - checkboxes for which data to show in which chart, aka a pane under the charts with relevant buttons
  // - reload data


  // read the tickers from setupfile
  val tickers = readTickers.fromFile("tickersConfig.txt")

  val buildStage = new StageBuilder(tickers)
  stage = buildStage.stageVariable

}

