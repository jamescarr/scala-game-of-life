import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info){
  val specs = "org.scala-tools.testing" % "specs" % "1.6.2.1"
}

