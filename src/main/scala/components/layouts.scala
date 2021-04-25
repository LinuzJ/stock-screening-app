package components

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, ComboBox, DatePicker, Label, ListView, SplitPane}
import scalafx.scene.layout.{BorderPane, HBox, Priority, VBox}

class Layouts {
  
  def buttonGrid(lt: Button, lb: Button, rt: Button, rb: Button): BorderPane = {
    val l = {
          val i = new VBox(15, lt, lb)
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
    HBox.setHgrow(l, Priority.Always)
    HBox.setHgrow(r, Priority.Always)
    l.setMaxSize(Double.MaxValue, Double.MaxValue)
    r.setMaxSize(Double.MaxValue, Double.MaxValue)
    new BorderPane(){
      center = hBox
    }
  }
  
  def dateGrid(lb: DatePicker, rb: DatePicker): BorderPane = {
    val lt = new Label("Start Date")
    val rt = new Label("End Date")
    lt.getStyleClass.add("dateLabel")
    rt.getStyleClass.add("dateLabel")
    val l = {
          val i = new VBox(15, lt, lb)
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
    HBox.setHgrow(l, Priority.Always)
    HBox.setHgrow(r, Priority.Always)
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


  def dataPanel(t: BorderPane, bl: ComboBox[String], br: ComboBox[String]): BorderPane = {
    val b: HBox = new HBox( bl, br)
    b.setAlignment(Pos.Center)
    HBox.setHgrow(bl, Priority.Always)
    HBox.setHgrow(br, Priority.Always)
    bl.setMaxSize(Double.MaxValue, Double.MaxValue)
    br.setMaxSize(Double.MaxValue, Double.MaxValue)

    val i = new VBox( t, b)
    i.setAlignment(Pos.Center)
    VBox.setVgrow(t, Priority.Always)
    VBox.setVgrow(b, Priority.Always)
    b.setMaxSize(Double.MaxValue, Double.MaxValue)
    t.setMaxSize(Double.MaxValue, Double.MaxValue)
    new BorderPane(){
      center = i
    }
  }

  def renderDataDashboard(l: SplitPane, r: SplitPane): SplitPane = {
      val c = new SplitPane()
      c.dividerPositions = 0.7
      c.items.add(l)
      c.items.add(r)
      c.orientation = scalafx.geometry.Orientation.Horizontal
      c
  }

  def generateDDRight(pie: BorderPane, dataPanel: BorderPane, controlPanel: BorderPane): SplitPane = {
      val r = new SplitPane()
      //--------------------------------
      val rt = new BorderPane(){ center = pie }
      //--------------------------------
      val rb = new SplitPane()
      val rbt = dataPanel
      val rbb = controlPanel
      rb.items.add(rbt)
      rb.items.add(rbb)
      rb.orientation = scalafx.geometry.Orientation.Vertical
      //--------------------------------
      r.items.add(rt)
      r.items.add(rb)
      r.orientation = scalafx.geometry.Orientation.Vertical
      r
  }

  def generateDDLeft(line: BorderPane, bar: BorderPane): SplitPane = {
      val l = new SplitPane()
      l.items.add{ new BorderPane(){ center     = line } }
      l.items.add{ new BorderPane(){ center     = bar} }
      l.orientation = scalafx.geometry.Orientation.Vertical
      l
  }

}
