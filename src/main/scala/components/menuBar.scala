package components

import scalafx.scene.control.{Menu, MenuBar, MenuItem}

class MenuBarTheme {
  def createMenu(theme: String): MenuBar = {
    val menu = new MenuBar {
      menus = List(
        new Menu("Theme") {
          items = List(
            new MenuItem("Green"),
            new MenuItem("Blue"),
            new MenuItem("Dark"),
            new MenuItem("Gray"),
            new MenuItem("Light")
          )
        })
    }
    menu
  }
}
object MenuBarTheme {
  def get(theme: String): MenuBar = {
    val i = new MenuBarTheme
    i.createMenu(theme)
  }
}
