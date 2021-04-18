package components

import scalafx.scene.control.{Button, ListView}
import scalafx.scene.layout.BorderPane

class tickerListPane(tickers: Seq[(String, String)]) {

  val listView    = new ListView(tickers.map(_._1))
  val button      = new Button("ADD")

  val mainPane: BorderPane = new BorderPane()

  mainPane.setTop(listView)
  mainPane.setBottom(button)
}

object tickerListPane {
  def getPane(source: Seq[(String, String)]): BorderPane = {
    val temp = new tickerListPane(source)
    temp.mainPane
  }
}