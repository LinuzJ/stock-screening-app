package charts
import scalafx.Includes._
import data.Data
import javafx.event.EventHandler
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.PieChart

class Pie(inputData: Seq[(String, Data)]) extends Chart {

  // method to get the pieChart
  def getChart: PieChart = thisChart

  private var thisChart: PieChart = createChart(inputData)

  // creation of the chart
  private def createChart(input: Seq[(String, Data)]): PieChart = {
    val product = new PieChart {
    title = "Total Volume"
    data = ObservableBuffer(input.map(pair => (pair._1, pair._2.getVolumeTotal)).map(x => PieChart.Data(x._1, x._2)))
    }
//      // interactive
//      var label: Label = new Label()
//      label.setTextFill(Color.Burlywood)
//      label.setStyle("-fx-font: 24 arial;")

//      product.getData.forEach{
//        dataPoint => {
//         dataPoint.getNode.addEventHandler(MouseEvent.MousePressed,
//             new EventHandler[MouseEvent] {
//               override def handle(event: MouseEvent): Unit = {
//                label.setTranslateX(event.getSceneX)
//                label.setTranslateY(event.getSceneY)
//                label.setText(dataPoint.getPieValue.toString + "%")
//               }
//             })
//        }
//      }

//      for (data <- chart.getData) {
//        data.getNode.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler[MouseEvent]() {
//          override def handle(e: MouseEvent): Unit = {
//            label.setTranslateX(e.getSceneX)
//            label.setTranslateY(e.getSceneY)
//            label.setText(String.valueOf(data.getPieValue) + "%")
//          }
//        })
//      }
      product.setTitle("Total volume over time")
      product
    }








//    for (dataPoint: PieChart.Data <- chart.getData) {
//      dataPoint.getNode.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler[MouseEvent]() {
//        override def handle(event: MouseEvent): Unit = {
//            label.setTranslateX(event.getSceneX)
//            label.setTranslateY(event.getSceneY)
//            label.setText(dataPoint.getPieValue.toString + "%")
//        }
//      })
//    }
  // method to update the chart
  override def update(newData: Seq[(String, Data)]): Unit = { thisChart = createChart(newData) }

}
