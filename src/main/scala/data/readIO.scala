package data

import java.io._

class readTickers {

   def readMyFile(sourceFile: String): Seq[(String, String)] = {
     var seqOfTickers: Seq[(String, String)] = List()
     try {
        // opens an incoming character stream from the file
        val fileIn = new FileReader(sourceFile)

        // Creates a buffered stream with which it is possible to
        // read line by line from the previous stream

        val linesIn = new BufferedReader(fileIn)

        // At this point, the streams should be open
        // so we must remember to close them.
        try {

          // Read the text from the stream line by line until the read line
          // is null. Then we know that
          // the stream (and also the file) end has been reached.

            var oneLine = linesIn.readLine()

            while (oneLine != null) {
                val split: Seq[String] = oneLine.split('-')
                seqOfTickers = seqOfTickers :+ (split.head, split.last)
                oneLine = linesIn.readLine()
              }
            seqOfTickers
          } finally {
            // Close open streams
            // This will be executed if the file has been opened
            // regardless of whether or not there were any exceptions.

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
    setup.readMyFile(source)
  }
}