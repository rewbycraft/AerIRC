import AssemblyKeys._

name := "aerirc"

version := "0.0.0"

scalaVersion := "2.11.2"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.2" % "test"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.3.6"

libraryDependencies += "org.reflections" % "reflections" % "0.9.9"

assemblySettings

assemblyOption in assembly ~= { _.copy(includeScala = false) }

publishTo := {
	val nexus = "https://nexus.roelf.org/"
	if (isSnapshot.value)
		Some("snapshots" at nexus + "content/repositories/snapshots") 
	else
		Some("releases"  at nexus + "content/repositories/releases")
}

credentials += Credentials(Path.userHome / "code" / "nexuscreds.txt")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

