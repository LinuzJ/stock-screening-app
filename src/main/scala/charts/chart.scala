package charts

import data.Data
import scalafx.scene.layout.BorderPane

abstract class Chart {
  //  This is an abstract class for all of the charts

  // The getChart that all of the chart classes have
  def getChart: scalafx.scene.chart.Chart

  // This creates a gridPane that will be used in the final display
  def getPane: BorderPane = {
      val pane = new BorderPane()
      pane.setCenter(getChart)
      pane
    }
  def roundDecimal(i: Double, decimals: Int): Double = {
    BigDecimal(i).setScale(decimals, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  // Method that updates the charts with new data
  def update(newData: Seq[(String, Data)]): Unit
}
// Object for accessing charts
object Charts {
  def bar(i: Seq[(String, Data)]) = new Bar(i)
  def line(i: Seq[(String, Data)]) = new Line(i)
  def pie(i: Seq[(String, Data)]) = new Pie(i)
}