package data

import scalafx.scene.control.Label
import scalafx.scene.control.ComboBox
import scalafx.scene.layout.{Border, BorderPane, VBox}

class DataPane(inputData: Seq[(String, Data)]) {



  private var thisPane: BorderPane = createPane(inputData, inputData.head._1)

  def update(input: Seq[(String, Data)]): Unit = { thisPane = createPane(input, input.head._1) }

  def changeStock(input: Seq[(String, Data)], ticker: String): Unit = { thisPane = createPane(input, ticker) }
  def getPane: BorderPane = thisPane

  def createPane(input: Seq[(String, Data)], stockToDisplay: String): BorderPane = {
    val newPane = new BorderPane()

    val insidePane: VBox = new VBox()

    var dataToUse: Data = {
      var temp: Data = input.head._2
      for (i <- input) {
        if (i._1 == stockToDisplay) {
          temp = i._2
        }
      }
      temp
    }
    insidePane.children.add(new Label("Stock: " + dataToUse.stock))
    insidePane.children.add(new Label("Total Volume: " + dataToUse.getVolumeTotal))



    newPane.setCenter(insidePane)

    newPane
  }
}
