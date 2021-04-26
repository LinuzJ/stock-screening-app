package components

class StocksToDisplay {

  def getStocks: Seq[String] = stocks

  // here you choose which stocks to monitor
  private var stocks: Seq[String] = List()

  // helper method for changing the stocks
  def changeStocks(input: Seq[String]): Unit = { stocks = input }

  // debug help
  override def toString: String = stocks.mkString(" + ")
}
