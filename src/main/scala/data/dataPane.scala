package data

import scalafx.scene.control.Label
import scalafx.scene.layout.{BorderPane, VBox}

class DataPane(inputData: Seq[(String, Data)]) {



  private var thisPane: BorderPane = createPane(inputData)

  def update(input: Seq[(String, Data)]): Unit = { thisPane = createPane(input) }

  def getPane: BorderPane = thisPane

  def createPane(input: Seq[(String, Data)]): BorderPane = {
    val newPane = new BorderPane()

    val insidePane: VBox = new VBox()
    val label1 = new Label("Data information 1")
    val label2 = new Label("Data information 2")
    val label3 = new Label("Data information 3")
    insidePane.children.add(label1)
    insidePane.children.add(label2)
    insidePane.children.add(label3)

    newPane.children.add(insidePane)

    newPane
  }
}
