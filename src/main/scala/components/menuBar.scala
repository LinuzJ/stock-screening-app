package components

import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Menu, MenuBar, MenuItem}

class MenuBarTheme {

  var menuOfIntrest: Option[Menu] = None

  def createMenuOfIntrest(t: Theme) = {
     menuOfIntrest = {
       Some(new Menu("Theme") {
                  items = List(
                    new MenuItem("Green"),
                    new MenuItem("Blue"),
                    new MenuItem("Gray"),
                    new MenuItem("Light")
                  )
                }
              )
     }
  }

  def createMenu(i: Option[Menu]): MenuBar = {
    val menu = new MenuBar {
      menus = List(

        new Menu("Settings") {
          mnemonicParsing = true
          items = List(
            i.get,
            new MenuItem("Help") {
               onAction = (e) => new Alert(AlertType.Information){
                  title = "Help"
                  headerText = "Here are some basic hints to get you going!"
                  contentText = "Add the stocks you want the analyse by choosing them on the setup page to the right and then either pressing A or the ADD button. If you want to delete the ticker you can press the little X next to the ticker to delete it.\n\n After the litte box with the ticker of the stock you have choosen has appeard you can press the 'Continue to Dashboard' button or press CTRL + TAB. \nIn the dashboard you can analyse the stocks you have chosen, change the time interval you are looking at and also the time interval in the data. There is also a litte window with some basic fatcs about the stock on the right.\n\n You can change the view from absolute price to relative price with the button found in the controlpanel on the bottom right"
               }.showAndWait()
            }
          )
        }
      )
    }
    menu
  }
}
object MenuBarTheme {
  val thisMenu = new MenuBarTheme
  private def init(t: Theme): Unit = {
    thisMenu.createMenuOfIntrest(t)
  }
  def get(t: Theme): MenuBar = {
    init(t)
    thisMenu.createMenu(thisMenu.menuOfIntrest)
  }

}
