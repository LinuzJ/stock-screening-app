package components

class Theme {
  var get: String = "Green"
  def changeTheme(i: String): Unit = { get = i }
}
object Theme {
  def newT: Theme = new Theme
}
