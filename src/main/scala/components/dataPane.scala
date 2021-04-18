package components

import data.Data
import scalafx.scene.control.Label
import scalafx.scene.layout.{BorderPane, VBox}

class DataPane(inputData: Seq[(String, Data)]) {


  private var thisPane: BorderPane = createPane(inputData, { if (inputData.isEmpty) "" else inputData.head._1 })

  def update(input: Seq[(String, Data)]): Unit = { thisPane = createPane(input, { try { input.head._1 } catch { case e: Throwable => "" } }) }

  def changeStock(input: Seq[(String, Data)], ticker: String): Unit = { thisPane = createPane(input, ticker) }

  def getPane: BorderPane = thisPane

  def createPane(input: Seq[(String, Data)], stockToDisplay: String): BorderPane = {
    try {
      val newPane = new BorderPane()
      val insidePane: VBox = new VBox()
      val mapped = input.toMap
      insidePane.children.add{
       val newButton =  new Label("Stock: " + mapped(stockToDisplay).stock)
       newButton.getStyleClass.add("test")
        newButton
      }
      insidePane.children.add{
       val newButton =  new Label("Total Volume: " + mapped(stockToDisplay).getVolumeTotal)
       newButton.getStyleClass.add("test")
        newButton
      }

      newPane.setCenter(insidePane)

      newPane
    } catch {
      case e: NoSuchElementException => new BorderPane{
        center = new Label("Failed to generate the data due to an error")
      }
    }

  }
}
