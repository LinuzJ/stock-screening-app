package components

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, DatePicker, Label}
import scalafx.scene.layout.{BorderPane, HBox, Priority, VBox}

class Layouts {
  
  def buttonGrid(lt: Button, lb: Button, rt: Button, rb: Button): BorderPane = {
    val l = {
          val i = new VBox(15, lt, lb)
          VBox.setMargin(i, Insets(20, 20, 20, 20))
          i.setAlignment(Pos.Center)
          VBox.setVgrow(lt, Priority.Always)
          VBox.setVgrow(lb, Priority.Always)
          lb.setMaxSize(Double.MaxValue, Double.MaxValue)
          lt.setMaxSize(Double.MaxValue, Double.MaxValue)
        i
      }
    val r = {
          val i = new VBox(15, rt, rb)
          VBox.setMargin(i, Insets(20, 20, 20, 20))
          i.setAlignment(Pos.Center)
          VBox.setVgrow(rt, Priority.Always)
          VBox.setVgrow(rb, Priority.Always)
          lb.setMaxSize(Double.MaxValue, Double.MaxValue)
          lt.setMaxSize(Double.MaxValue, Double.MaxValue)
        i
    }
    val hBox: HBox = new HBox(15, l, r)
    hBox.setAlignment(Pos.Center)
    HBox.setHgrow(lt, Priority.Always)
    HBox.setHgrow(lb, Priority.Always)
    l.setMaxSize(Double.MaxValue, Double.MaxValue)
    r.setMaxSize(Double.MaxValue, Double.MaxValue)
    new BorderPane(){
      center = hBox
    }
  }
  
  def dateGrid(lb: DatePicker, rb: DatePicker): BorderPane = {
    val lt = new Label("Start Date")
    val rt = new Label("End Date")
    val l = {
          val i = new VBox(10, lt, lb)
          VBox.setMargin(i, Insets(10, 10, 10, 10))
          i.setAlignment(Pos.Center)
          VBox.setVgrow(lt, Priority.Always)
          VBox.setVgrow(lb, Priority.Always)
          lb.setMaxSize(Double.MaxValue, Double.MaxValue)
          lt.setMaxSize(Double.MaxValue, Double.MaxValue)
        i
      }
    val r = {
          val i = new VBox(15, rt, rb)
          VBox.setMargin(i, Insets(10, 10, 10, 10))
          i.setAlignment(Pos.Center)
          VBox.setVgrow(rt, Priority.Always)
          VBox.setVgrow(rb, Priority.Always)
          lb.setMaxSize(Double.MaxValue, Double.MaxValue)
          lt.setMaxSize(Double.MaxValue, Double.MaxValue)
        i
    }
    val hBox: HBox = new HBox(15, l, r)
    hBox.setAlignment(Pos.Center)
    HBox.setHgrow(lt, Priority.Always)
    HBox.setHgrow(lb, Priority.Always)
    l.setMaxSize(Double.MaxValue, Double.MaxValue)
    r.setMaxSize(Double.MaxValue, Double.MaxValue)
    new BorderPane(){
      center = hBox
    }
  }

  def controlPanel(t: BorderPane, b: BorderPane): BorderPane = {
    val vBox = new VBox(15, t, b)
    VBox.setMargin(vBox, Insets(10, 10, 10, 10))
    vBox.setAlignment(Pos.Center)
    VBox.setVgrow(t, Priority.Always)
    VBox.setVgrow(b, Priority.Always)
    t.setMaxSize(Double.MaxValue, Double.MaxValue)
    b.setMaxSize(Double.MaxValue, Double.MaxValue)
    new BorderPane(){
      center = vBox
    }
  }
}
