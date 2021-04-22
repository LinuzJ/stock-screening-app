import data.readTickers
import scalafx.application.JFXApp


object Main extends JFXApp {

  // TODO
  // - lineChart adjustability between absolute and relative data KIND OF DONE?
  // - Error handling for the data fetching
  // - Control Panel
  // - reload data


  // read the tickers from setupfile
  val tickers = readTickers.fromFile("tickersConfig.txt")

  val buildStage = new StageBuilder(tickers)
  stage = buildStage.stageVariable

}

