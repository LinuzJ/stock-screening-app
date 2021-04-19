package components

import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{HBox, VBox}

class TickerDisplayBox(ticker: String) {
  val pane: HBox        = new HBox
  pane.getStyleClass.add("tickerDisplay")

  val label: Label      = new Label(ticker)
  val button: Button    = new Button("X")

  button.setPrefSize(20, 20)
  pane.children.add(label)
  pane.children.add(button)

  def getBox: HBox = {
    pane
  }
}

