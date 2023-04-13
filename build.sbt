ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.2"

lazy val root = (project in file("."))
  .settings(
    name := "Producer"
  )

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.4.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.3.6"