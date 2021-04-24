import data.readTickers
import scalafx.application.JFXApp


object Main extends JFXApp {

  // TODO
  // - Control Panel
  // - TESTING ????????????????
  //  KNOWN BUGS
  // - xaxis when chaning dates
  // - datapane with emtyp sets error

  // read the tickers from setupfile
  val tickers = readTickers.fromFile("tickersConfig.txt")

  stage = StageBuilder.newStage(tickers).stageVariable

}

