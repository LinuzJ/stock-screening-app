package components

import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.HBox

class TickerDisplayBox(ticker: String) {
  val pane: HBox        = new HBox
  pane.getStyleClass.add("controlPanelButton")

  val label: Label      = new Label(ticker)
  val button: Button    = new Button("X")

  button.setPrefSize(20, 20)
  pane.children.add(label)
  pane.children.add(button)

  def getBox: HBox = {
    pane
  }
}

