package components

import data.Data
import scalafx.application.JFXApp
import scalafx.scene.control.{Label, ListView}
import scalafx.scene.layout.{BorderPane, VBox}

class DataPane(inputData: Seq[(String, Data)], t: Seq[(String, String)]) {

  private var s2d: Option[String] = None

  private var thisPane: BorderPane = createPane(inputData)

  def update(input: Seq[(String, Data)]): Unit = { s2d = Some(input.head._1); thisPane = createPane(input) }

  def changeStock(input: Seq[(String, Data)], ticker: String): Unit = { s2d = Some(ticker); thisPane = createPane(input) }

  def getPane: BorderPane = thisPane

  def createPane(i: Seq[(String, Data)]): BorderPane = {
    if (s2d.isDefined) {
      val map = i.toMap
      val t2map = t.map(x => (x._2, x._1)).toMap
      val labels2Add: Seq[String] = Seq(
        t2map(s2d.get),
        "Total Volume: " + map(s2d.get).getVolumeTotal,
        "Average Price: " + { (map(s2d.get).getFormatted.map(_._2).sum)/(map(s2d.get).getFormatted.map(_._2).length) } + " $"
      )
      new BorderPane(){
        center = new ListView(labels2Add)
      }
    } else {
      println("error")
       new BorderPane(){
        center = new ListView()
      }
    }
  }

//  def createPane(input: Seq[(String, Data)], stockToDisplay: String): BorderPane = {
//    try {
//      val newPane = new BorderPane()
//      val insidePane: VBox = new VBox()
//      val mapped = input.toMap
//      insidePane.children.add{
//       val newButton =  new Label("Stock: " + mapped(stockToDisplay).stock)
//       newButton.getStyleClass.add("test")
//        newButton
//      }
//      insidePane.children.add{
//       val newButton =  new Label("Total Volume: " + mapped(stockToDisplay).getVolumeTotal)
//       newButton.getStyleClass.add("test")
//        newButton
//      }
//
//      newPane.setCenter(insidePane)
//
//      newPane
//    } catch {
//      case e: NoSuchElementException => new BorderPane{
//        center = new Label("Failed to generate the data due to an error")
//      }
//    }
//
//  }
}
