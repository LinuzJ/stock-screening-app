package components

import scalafx.application.JFXApp
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

class ErrorPopup {
          def thisError(tText: String, hText: String, cText: String, stage: JFXApp.PrimaryStage) = {
              new Alert(AlertType.Error) {
                initOwner(stage)
                title = tText
                headerText = hText
                contentText = cText
              }.showAndWait()
          }
}
object ErrorPopup {
  def getPopup(tText: String, hText: String, cText: String, stage: JFXApp.PrimaryStage) = {
    val thisNewError = new ErrorPopup
    thisNewError.thisError(tText, hText, cText, stage)
  }
}
