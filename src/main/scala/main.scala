import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.Pane
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
//import scala.util.parsing.json._

import scalaj.http._



object Main extends JFXApp {
    /*
  Creation of a new primary stage (Application window).
  We can use Scala's anonymous subclass syntax to get quite
  readable code.
  */
  val response: HttpResponse[String] = Http("https://query1.finance.yahoo.com/v8/finance/chart/AAPL?interval=5m").asString

//  val parseResult = parse(response.body).getOrElse(Json.Null)
//  val cursor = parseResult.hcursor
//
//
//
//  println(parseResult)


  stage = new JFXApp.PrimaryStage {
      title.value = "Cool ass stonks graph"
      width = 600
      height = 450
  }



  val root = new Pane //Simple pane component
  val scene = new Scene(root) //Scene acts as a container for the scene graph
  stage.scene = scene

  root.children = new Label(response.body)


}

