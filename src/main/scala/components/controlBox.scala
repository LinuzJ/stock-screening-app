package components

import scalafx.scene.control.ComboBox

class ControlBox(tickers: Seq[String]) {

  var thisBox: ComboBox[String] = createBox(tickers)

  def update(newData: Seq[String]): Unit = { thisBox = createBox(newData) }

  private def createBox(value: Seq[String]): ComboBox[String] = {

      val newbox = new ComboBox[String](value)

      newbox.getSelectionModel.select{ if (value.nonEmpty) value.head else "" }

      newbox
  }
}


