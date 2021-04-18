package components

import scalafx.scene.control.ComboBox

class ControlBox(tickers: Seq[String]) {

  private var thisPane: ComboBox[String] = createBox(tickers)

  def getBox = thisPane

  def update(newData: Seq[String]): Unit = { thisPane = createBox(newData) }

  private def createBox(value: Seq[String]): ComboBox[String] = {

      val newbox = new ComboBox[String](value)

      newbox.getSelectionModel.select{ if (value.nonEmpty) value.head else "" }

      newbox
  }
}


