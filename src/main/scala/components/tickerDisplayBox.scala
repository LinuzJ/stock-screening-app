package components

import scalafx.geometry.Pos
import scalafx.scene.control.Label
import scalafx.scene.layout.HBox

class TickerDisplayBox(t: String) {


    val label: Label      = new Label(t)
    val button: Label    = new Label("X")
    button.getStyleClass.add("ButtonLabel")


    val pane: HBox         = new HBox(10, label, button)
    pane.getStyleClass.add("tickerDisplay")
    pane.setAlignment(Pos.Center)

    def getBox: HBox = pane
}
object TickerDisplayBox {
  def get(t: String) = {
    new TickerDisplayBox(t)
  }
}
