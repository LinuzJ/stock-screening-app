import scalafx.scene.control.ListView

package object Components {

  def roundDecimal(i: Double, decimals: Int): Double = {
      BigDecimal(i).setScale(decimals, BigDecimal.RoundingMode.HALF_UP).toDouble
  }
  def mean(i: Seq[Double]): Double = i.sum / i.size

}
