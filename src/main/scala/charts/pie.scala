package charts

import data.Data
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.PieChart

class pie(inputData: Seq[(String, Data)]) {

    def getChart: PieChart = createPieChart

    // creation of the chart
    private def createPieChart: PieChart = {
      val product = new PieChart {
      title = "Total Volume"
      data = ObservableBuffer(calculateVolume.map(x => PieChart.Data(x._1, x._2)))
      }
      product.setPrefSize(300, 300)
      product
    }
    // data in more readable format
    private val calculateVolume: Seq[(String, Int)] = inputData.map(pair => (pair._1, pair._2.getVolumeTotal))
}
