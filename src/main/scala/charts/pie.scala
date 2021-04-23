package charts
import data.Data
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.PieChart
import scalafx.scene.control.Tooltip
import scalafx.util.Duration

class Pie(inputData: Seq[(String, Data)]) extends Chart {

  // method to get the pieChart
  def getChart: PieChart = thisChart

  private var thisChart: PieChart = createChart(inputData)

  // creation of the chart
  private def createChart(input: Seq[(String, Data)]): PieChart = {
    val newPieChart = new PieChart {
    title = "Total Volume"
    data = ObservableBuffer(input.map(pair => (pair._1, pair._2.getVolumeTotal)).map(x => PieChart.Data(x._1, x._2)))
    }
    val totalVolume = { var i: Long = 0; newPieChart.getData.forEach(x => i += x.getPieValue.toLong); i }
    newPieChart.getData.forEach(i => {
          Tooltip.install(
               i.getNode,
                new Tooltip(s"Volume: ${i.getName}\n${(i.getPieValue/totalVolume)*100}%\n${i.getPieValue}"){
                  showDelay = Duration.Zero
                  showDuration = Duration.Indefinite
            }
          )
      }
    )
    newPieChart.setTitle("Total volume over time")
    newPieChart
  }

  // method to update the chart
  override def update(newData: Seq[(String, Data)]): Unit = { thisChart = createChart(newData) }

}
