package components

import scalafx.scene.control.{Menu, MenuBar, MenuItem}

class MenuBarTheme {
  def createMenu(t: Theme): MenuBar = {
    val menu = new MenuBar {
      menus = List(

        new Menu("Settings") {
          mnemonicParsing = true
          items = List(

            new Menu("Theme") {
              items = List(
                new MenuItem("Green") {
                  onAction = (e) => {
                    t.changeTheme(text.value)
                  }
                },
                new MenuItem("Blue") {
                  onAction = (e) => {
                    t.changeTheme(text.value)
                  }
                },
                new MenuItem("Dark")  {
                  onAction = (e) => {
                    t.changeTheme(text.value)
                  }
                },
                new MenuItem("Gray") {
                  onAction = (e) => {
                    t.changeTheme(text.value)
                  }
                },
                new MenuItem("Light") {
                  onAction = (e) => {
                    t.changeTheme(text.value)
                  }
                }
              )
            }
          )
        })
    }
    menu
  }
}
object MenuBarTheme {
  def get(t: Theme): MenuBar = {
    val i = new MenuBarTheme
    i.createMenu(t)
  }
}
