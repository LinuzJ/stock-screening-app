import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.Pane
import data.Datak

object Main extends JFXApp {

  stage = new JFXApp.PrimaryStage {
      title.value = "Cool ass stonks graph"
      width = 800
      height = 650
  }
  val a = new Datak

  val root = new Pane //Simple pane component
  val scene = new Scene(root) //Scene acts as a container for the scene graph
  stage.scene = scene
  println(a.getResonse)
  root.children = new Label("hej")

}

