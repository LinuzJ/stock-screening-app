package components

import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.control.{Button, ComboBox, DatePicker, Label, ListView, SplitPane}
import scalafx.scene.layout.{BorderPane, FlowPane, HBox, Priority, TilePane, VBox}

class Layouts {
  /*
  * This Class is filled with layout panes/ helper methods for the stagebuilder
  *
  *
  * */

  def buttonGrid(lt: Button, lb: Button, rt: Button, rb: Button): BorderPane = {
    val l = {
          VBox.setVgrow(lt, Priority.Always)
          VBox.setVgrow(lb, Priority.Always)
          lb.setMaxSize(Double.MaxValue, Double.MaxValue)
          lt.setMaxSize(Double.MaxValue, Double.MaxValue)
          val i = new VBox(10, lt, lb)
          VBox.setMargin(i, Insets(10, 10, 10, 10))
          i.setAlignment(Pos.Center)
        i
      }
    val r = {
          VBox.setVgrow(rb, Priority.Always)
          VBox.setVgrow(rt, Priority.Always)
          lb.setMaxSize(Double.MaxValue, Double.MaxValue)
          lt.setMaxSize(Double.MaxValue, Double.MaxValue)
          val i = new VBox(10, rt, rb)
          VBox.setMargin(i, Insets(10, 10, 10, 10))
          i.setAlignment(Pos.Center)
        i
    }
    HBox.setHgrow(l, Priority.Always)
    HBox.setHgrow(r, Priority.Always)
    l.setMaxSize(Double.MaxValue, Double.MaxValue)
    r.setMaxSize(Double.MaxValue, Double.MaxValue)
    val hBox: HBox = new HBox(10, l, r)
    hBox.setAlignment(Pos.Center)
    new BorderPane(){
      center = hBox
    }
  }
  
  def dateGrid(lb: DatePicker, rb: DatePicker): BorderPane = {
    val lt = new Label("Start Date")
    val rt = new Label("End Date")
    lt.getStyleClass.add("dateLabel")
    rt.getStyleClass.add("dateLabel")

    lb.setMaxSize(Double.MaxValue, Double.MaxValue)
    lt.setMaxSize(Double.MaxValue, Double.MaxValue)
    rb.setMaxSize(Double.MaxValue, Double.MaxValue)
    rt.setMaxSize(Double.MaxValue, Double.MaxValue)

    val t = {
      val h = new HBox(10, lt, rt)
      HBox.setHgrow(lt, Priority.Always)
      HBox.setHgrow(rt, Priority.Always)
      h.setAlignment(Pos.Center)
      h
    }
    val b = {
      val h = new HBox(10, lb, rb)
      HBox.setHgrow(lb, Priority.Always)
      HBox.setHgrow(rb, Priority.Always)
      h.setAlignment(Pos.Center)
      h
    }

    val v: VBox = new VBox(10, t, b)
    VBox.setVgrow(t, Priority.Always)
    VBox.setVgrow(b, Priority.Always)
    t.setMaxSize(Double.MaxValue, Double.MaxValue)
    b.setMaxSize(Double.MaxValue, Double.MaxValue)
    v.setAlignment(Pos.Center)

    new BorderPane(){
      center = v
    }
  }

  def controlPanel(t: BorderPane, b: BorderPane): BorderPane = {
    VBox.setVgrow(t, Priority.Always)
    VBox.setVgrow(b, Priority.Always)
    t.setMaxSize(Double.MaxValue, Double.MaxValue)
    b.setMaxSize(Double.MaxValue, Double.MaxValue)
    val vBox = new VBox(10, t, b)
    VBox.setMargin(vBox, Insets(10, 10, 10, 10))
    vBox.setAlignment(Pos.Center)
    new BorderPane(){
      center = vBox
    }
  }


  def dataPanel(t: BorderPane, bl: ComboBox[String], br: ComboBox[String]): BorderPane = {

    HBox.setHgrow(bl, Priority.Always)
    HBox.setHgrow(br, Priority.Always)
    bl.setMaxSize(Double.MaxValue, Double.MaxValue)
    br.setMaxSize(Double.MaxValue, Double.MaxValue)
    val b: HBox = new HBox(bl, br)
    b.setAlignment(Pos.Center)

    val i = new VBox( t, b)
    i.setAlignment(Pos.Center)
    VBox.setVgrow(i, Priority.Always)
    i.setMaxSize(Double.MaxValue, Double.MaxValue)
    new BorderPane(){
      center = i
    }
  }

  def renderDataDashboard(l: SplitPane, r: SplitPane): SplitPane = {
      val c = new SplitPane()
      c.dividerPositions = 0.65
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
  
  def renderSetup(l: ListView[String], r: BorderPane): SplitPane = {
      val c = new SplitPane()
      c.items.add(l)
      c.items.add(r)
      c.orientation = scalafx.geometry.Orientation.Horizontal
      c
  }
  
  def renderFlow: FlowPane = {
      val rt = new FlowPane()
      rt.getStyleClass.add("flowpane")
      rt.setVgap(10)
      rt.setHgap(10)
      rt.getStyleClass.add("splitRightTopInside")
      rt
  }
  
  def buttonGrid(b2d: Button, b2e: Button, b2a: Button): VBox = {
    val bBox: HBox = new HBox(10, b2d, b2e)

      bBox.setAlignment(Pos.Center)
      HBox.setHgrow(b2d, Priority.Always)
      HBox.setHgrow(b2e, Priority.Always)
      VBox.setVgrow(b2a, Priority.Always)
      b2d.setMaxSize(Double.MaxValue, Double.MaxValue)
      b2e.setMaxSize(Double.MaxValue, Double.MaxValue)
      b2a.setMaxSize(Double.MaxValue, Double.MaxValue)

    val i: VBox = new VBox(15, b2a, bBox)
    VBox.setMargin(i, Insets(15, 15, 15, 15))
      i
  }

}
