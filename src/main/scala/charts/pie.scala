package charts
import scalafx.Includes._
import data.Data
import javafx.event.EventHandler
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.PieChart
import scalafx.scene.control.Label
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color


class pie(inputData: Seq[(String, Data)]) {

    // creation of the chart
    private val chart: PieChart = {
      val product = new PieChart {
      title = "Total Volume"
      data = ObservableBuffer(inputData.map(pair => (pair._1, pair._2.getVolumeTotal)).map(x => PieChart.Data(x._1, x._2)))
      }

//      // interactive
//      var label: Label = new Label()
//      label.setTextFill(Color.Burlywood)
//      label.setStyle("-fx-font: 24 arial;")
//
////      product.getData.forEach{
////        dataPoint => {
////         dataPoint.getNode.addEventHandler(MouseEvent.MousePressed,
////             new EventHandler[MouseEvent] {
////               override def handle(event: MouseEvent): Unit = {
////                label.setTranslateX(event.getSceneX)
////                label.setTranslateY(event.getSceneY)
////                label.setText(dataPoint.getPieValue.toString + "%")
////               }
////             })
////        }
////      }
//
//      for (data <- chart.getData) {
//        data.getNode.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler[MouseEvent]() {
//          override def handle(e: MouseEvent): Unit = {
//            label.setTranslateX(e.getSceneX)
//            label.setTranslateY(e.getSceneY)
//            label.setText(String.valueOf(data.getPieValue) + "%")
//          }
//        })
//      }

      product.setPrefSize(300, 300)
      product
    }
//
//  .setOnMouseEntered(
//            new EventHandler[MouseEvent] {
//               override def handle(event: MouseEvent): Unit = {
//                label.setTranslateX(event.getSceneX)
//                label.setTranslateY(event.getSceneY)
//                label.setText(dataPoint.getPieValue.toString + "%")
//               })
//            label.setTranslateX(event)
//            label.setTranslateY(event.getSceneY)
//            label.setText(dataPoint.getPieValue.toString + "%")
//         }






//    for (dataPoint: PieChart.Data <- chart.getData) {
//      dataPoint.getNode.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler[MouseEvent]() {
//        override def handle(event: MouseEvent): Unit = {
//            label.setTranslateX(event.getSceneX)
//            label.setTranslateY(event.getSceneY)
//            label.setText(dataPoint.getPieValue.toString + "%")
//        }
//      })
//    }
    for (i <- 0 to 10) {
      println("hej")
    }

    // method to get the pieChart
    def getChart: PieChart = chart
}
