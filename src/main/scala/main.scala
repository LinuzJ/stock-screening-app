import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.Pane
import data.Data
import java.time.Instant

object Main extends JFXApp {

  stage = new JFXApp.PrimaryStage {
      title.value = "Data dashboard"
      width = 800
      height = 650
  }

  // timeframe for graph (days)
  val time: Int = 4
  val unixTime: Long = Instant.now.getEpochSecond - (time * 24*60*60)

  // interval for graph (minutes)
  val interval = 5


  val data1 = new Data(s"https://query1.finance.yahoo.com/v8/finance/chart/AAPL?symbol=AAPL&period1=${unixTime}&period2=9999999999&interval=${interval}m")
  val data2 = new Data(s"https://query1.finance.yahoo.com/v8/finance/chart/NOK?symbol=AAPL&period1=${unixTime}&period2=9999999999&interval=${interval}m")
  val data3 = new Data(s"https://query1.finance.yahoo.com/v8/finance/chart/AAPL?symbol=BABA&period1=${unixTime}&period2=9999999999&interval=${interval}m")

  val root = new Pane
  val scene = new Scene(root)

  stage.scene = scene

  println(data1.getTimestamp)

  root.children = new Label("nothing here yet")

}

