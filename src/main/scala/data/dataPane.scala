package data

import scalafx.scene.control.Label
import scalafx.scene.layout.{Border, BorderPane, VBox}

class DataPane(inputData: Seq[(String, Data)]) {



  private var thisPane: BorderPane = createPane(inputData)

  def update(input: Seq[(String, Data)]): Unit = { thisPane = createPane(input) }

  def getPane: BorderPane = thisPane

  def createPane(input: Seq[(String, Data)]): BorderPane = {
    val newPane = new BorderPane()

    val insidePane: VBox = new VBox()
    input.foreach{
      stock => {
        insidePane.children.add{
          val temp = new Label(stock._1)
          temp.setPrefSize(100, 100)
          temp

        }
      }
    }

    newPane.children.add(insidePane)

    newPane
  }
}
