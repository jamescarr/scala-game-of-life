import sbt._

class Project(info:ProjectInfo) extends DefaultProject(info) {
  val scalaSwing = "org.scala-lang" % "scala-swing" % "2.8.0"
}
