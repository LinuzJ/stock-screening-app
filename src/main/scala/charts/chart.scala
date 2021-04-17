package charts

import data.Data
import scalafx.scene.control.CheckBox
import scalafx.scene.layout.{FlowPane, GridPane, VBox}

abstract class Chart {
  //  This is an abstract class for all of the charts

  // The getChart that all of the chart classes have
  def getChart: scalafx.scene.chart.Chart

  // This creates a gridPane that will be used in the final display
  def getPane: FlowPane = {
      val pane = new FlowPane()
      pane.children.add(getChart)
      pane
    }

  // Method that updates the charts with new data
  def update(newData: Seq[(String, Data)]): Unit
}
