import data.readTickers
import org.junit.jupiter.api.extension.ExtendWith
import org.testfx.framework.junit5.ApplicationExtension
import org.testfx.framework.junit5.Start
import javafx.stage.Stage
import org.junit.jupiter.api.Test
import org.testfx.api.FxRobot

//Test class definition. Use JUnit 5 extension mechanic.
@ExtendWith(Array(classOf[ApplicationExtension]))
class StageBuilderTest {

    var gui: Option[StageBuilder] = None

    @Start
    def start(stage: Stage): Unit = {
        val tickers = readTickers.fromFile("tickersConfig.txt")
        val newGui = StageBuilder.newStage(tickers)
        stage.setScene(newGui.stageVariable.getScene)

        //gui reference
        gui = Some(newGui)

        stage.show()

    }


    def testButtonToDashboard(robot: FxRobot): Unit = {

        gui match {
            case Some(gui) => {
                assert(gui.stageVariable.title.value == "Setup", "Stage title was Setup while at setup")

                //Make robot click the button.
                robot.clickOn(gui.buttonToDashboard)

                assert(gui.stageVariable.title.value == "Data dashboard", "Stage changed correctly!")
            }
            case None =>
        }
    }

    @Test
    def testData(robot: FxRobot): Unit = {
        gui match {
            case Some(gui) => {
                val currentData = gui.stockData
                robot.clickOn(gui.updateButton)
                assert(gui.stockData != currentData, "New data created on refresh!")
            }
            case None =>
        }

    }
}