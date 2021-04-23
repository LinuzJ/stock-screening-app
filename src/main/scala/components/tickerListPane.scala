package components

import scalafx.scene.control.{Button, ListView}
import scalafx.scene.layout.BorderPane

class TickerListPane(tickers: Seq[(String, String)]) {

  val listView    = new ListView(tickers.map(_._1))
  val button      = new Button("ADD")
  button.getStyleClass.add("controlPanelButton")

  val mainPane: BorderPane = new BorderPane()

  mainPane.setTop(listView)
  mainPane.setBottom(button)
}

object TickerListPane {
  def getPane(source: Seq[(String, String)]): BorderPane = {
    val temp = new TickerListPane(source)
    temp.mainPane
  }
}