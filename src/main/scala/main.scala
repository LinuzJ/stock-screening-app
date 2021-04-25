import data.readTickers
import scalafx.application.JFXApp


object Main extends JFXApp {

  // TODO
  // - xaxis when chaning dates

  // read the tickers from setupfile
  val tickers = readTickers.fromFile("tickersConfig.txt")

  // render the stage from the StageBuilder instance
  stage = StageBuilder.newStage(tickers).stageVariable

}

