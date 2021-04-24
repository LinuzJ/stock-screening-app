package components

import scalafx.scene.control.{Button, ListView}
import scalafx.scene.layout.BorderPane

class TickerListPane(tickers: Seq[(String, String)]) {

  val listView    = new ListView(tickers.map(_._1))

  val mainPane: BorderPane = new BorderPane()

  mainPane.setTop(listView)
}

object TickerListPane {
  def mkPane(source: Seq[(String, String)]): BorderPane = {
    val temp = new TickerListPane(source)
    temp.mainPane
  }
}