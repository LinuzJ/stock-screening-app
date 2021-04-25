package components

import scalafx.scene.control.{Menu, MenuBar, MenuItem}

class MenuBarTheme {

  var menuOfIntrest: Option[Menu] = None

  def createMenuOfIntrest(t: Theme) = {
     menuOfIntrest = {
       Some(new Menu("Theme") {
                  items = List(
                    new MenuItem("Green"),
                    new MenuItem("Blue"),
                    new MenuItem("Dark"),
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
          items = List(i.get)
        })
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
