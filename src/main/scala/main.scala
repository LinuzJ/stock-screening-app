import data.readTickers
import scalafx.application.JFXApp


object Main extends JFXApp {

  // read the tickers from setupfile
  val tickers = readTickers.fromFile("tickersConfig.txt")

  // render the stage from a StageBuilder instance
  stage = StageBuilder.newStage(tickers).stageVariable

}

