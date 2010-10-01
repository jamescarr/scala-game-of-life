import sbt._

class Project(info:ProjectInfo) extends DefaultProject(info) {
  val scalaToolsSnapshots = "Scala Tools Snapshots" at "http://scala-tools.org/repo-snapshots/"
  val scalaSwing = "org.scala-lang" % "scala-swing" % "2.8.0"
  val specsDependency = "org.scala-tools.testing" %% "specs" % "1.6.5" % "test" withSources
  val scalazCore = "com.googlecode.scalaz" %% "scalaz-core" % "5.0"
}
