
name := "data-dashboard"

version := "0.1"

scalaVersion := "2.13.4"

libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.2"
libraryDependencies += "org.scalafx" %% "scalafx" % "14-R19"

val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
).map(_ % circeVersion)

// Determine OS version of JavaFX binaries
lazy val osName = System.getProperty("os.name") match {
case n if n.startsWith("Linux") => "linux"
case n if n.startsWith("Mac") => "mac"
case n if n.startsWith("Windows") => "win"
case _ => throw new Exception("Unknown platform!")
}

// Add JavaFX dependencies, as these are required by ScalaFx
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map( m=>
"org.openjfx" % s"javafx-$m" % "14.0.1" classifier osName
)

//Testfx
libraryDependencies += "org.testfx" % "testfx-junit5" % "4.0.16-alpha" % Test

//Junit
libraryDependencies += "org.junit.jupiter" % "junit-jupiter-api" % "5.7.0-M1" % Test
libraryDependencies += "org.junit.jupiter" % "junit-jupiter-engine" % "5.7.0-M1" % Test
libraryDependencies += "org.junit.platform" % "junit-platform-runner" % "1.7.0-M1" % Test

//Junit5 interface, that allows running Junit tests from Scala
//This requires a plugin in project/plugins.sbt
resolvers in ThisBuild += Resolver.jcenterRepo
libraryDependencies ++= Seq(
    "net.aichler" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test
)
