import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.Pane
import data.Data

object Main extends JFXApp {

  stage = new JFXApp.PrimaryStage {
      title.value = "Data dashboard"
      width = 800
      height = 650
  }
  val a = new Data

  val root = new Pane
  val scene = new Scene(root)

  stage.scene = scene

  root.children = new Label("nothing here yet")

}

