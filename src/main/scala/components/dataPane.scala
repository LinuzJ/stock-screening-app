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
        "Max price: " + { Components.roundDecimal(map(s2d.get).getFormatted.map(_._2).max, 4) } + " $",
        "Min price: " + { Components.roundDecimal(map(s2d.get).getFormatted.map(_._2).min, 4) } + " $",
        "Average Price: " + { Components.roundDecimal(Components.mean(map(s2d.get).getFormatted.map(_._2)), 4) } + " $",
        "Variance: " + { map(s2d.get).getVariance(4) },
        "Standard deviation : " + { map(s2d.get).getStdDev(4) },
        "Total Volume: " + map(s2d.get).getVolumeTotal

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
}

