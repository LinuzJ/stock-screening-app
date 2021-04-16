package charts

import data.Data
import scalafx.scene.control.CheckBox
import scalafx.scene.layout.GridPane

abstract class Chart {
  //  This is an abstract class for all of the charts

  // The getChart that all of the chart classes have
  def getChart: scalafx.scene.chart.Chart

  // This creates a gridPane that will be used in the final display
  def getPane: GridPane = {
      val pane = new GridPane()
      pane.add(getChart, 0, 0)
      pane.add(new CheckBox("TestBox"), 0, 1)
      pane.add(new CheckBox("TexMexBox"), 1, 1)
      pane
    }

  // Method that updates the charts with new data
  def update(newData: Seq[(String, Data)]): Unit
}
