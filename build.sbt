import AssemblyKeys._

name := "aerirc"

version := "0.1-SNAPSHOT"

organization := "org.roelf.scala"

organizationName := "Roelf Software"

organizationHomepage := Some(url("http://roelf.org"))

scalaVersion := "2.11.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.2" % "test"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.6"

libraryDependencies += "org.reflections" % "reflections" % "0.9.9"

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.4"

assemblySettings

assemblyOption in assembly ~= { _.copy(includeScala = false) }

publishTo := {
	val nexus = "https://nexus.roelf.org/nexus/"
	if (isSnapshot.value)
		Some("snapshots" at nexus + "content/repositories/snapshots") 
	else
		Some("releases"  at nexus + "content/repositories/releases")
}

credentials += Credentials(Path.userHome / "code" / "nexuscreds.txt")

credentials += Credentials(Path.absolute(new File("/nexuscreds.txt")))

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

