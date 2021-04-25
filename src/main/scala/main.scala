import data.readTickers
import scalafx.application.JFXApp


object Main extends JFXApp {

  // TODO
  // - tickers boxes visuals
  // - theme adjustments
  // - Control Panel
  // - TESTING ????????????????
  //  KNOWN BUGS
  // - xaxis when chaning dates

  // read the tickers from setupfile
  val tickers = readTickers.fromFile("tickersConfig.txt")

  stage = StageBuilder.newStage(tickers).stageVariable

}

