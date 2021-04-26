package data

import java.io._

class readTickers {

  // Basic file reader that skips rows with #
   def fromFile(sourceFile: String): Seq[(String, String)] = {
     var seqOfTickers: Seq[(String, String)] = List()

     try {
        val fileIn = new FileReader(sourceFile)
        val linesIn = new BufferedReader(fileIn)

        try {
            var oneLine = linesIn.readLine()
            while (oneLine != null) {
              oneLine.take(1) match {
                case "#" => oneLine = linesIn.readLine()
                case _ => {
                    val split: Seq[String] = oneLine.split("->")
                    seqOfTickers = seqOfTickers :+ (split.head, split.last)
                    oneLine = linesIn.readLine()
                }
              }
            }
            seqOfTickers
          } finally {
            fileIn.close()
            linesIn.close()
            seqOfTickers
          }
        } catch {
          case notFound: FileNotFoundException => {
            println("File not found")
            seqOfTickers
          }
          case e: IOException => {
            println("Reading failed")
            seqOfTickers
          }
      }
   }
}
object readTickers {
  def fromFile(source: String) = {
    val setup = new readTickers
    setup.fromFile(source)
  }
}