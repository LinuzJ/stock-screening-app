package components

import scalafx.geometry.Insets
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, VBox}

object Layouts {
  def buttonGrid(lt: Button, lb: Button, rt: Button, rb: Button): BorderPane = {
    val p = new BorderPane()
      p.setLeft{
          val i = new VBox()
          VBox.setMargin(i, Insets(20, 20, 20, 20))
          i.children.add{
          val b = lt
          b
        }
          i.children.add{
          val b = lb
          b
        }
        i
    }
    p.setRight{
        val i = new VBox()
        VBox.setMargin(i, Insets(20, 20, 20, 20))
        i.children.add{
        val b = rt
        b
      }
        i.children.add{
        val b = rb
        b
      }
      i
    }
    BorderPane.setMargin(p, Insets(20, 20, 20, 20))
    p
  }
}
