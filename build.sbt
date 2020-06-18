name := "alpacascala"

version := "0.1"

scalaVersion := "2.13.2"

val versions = new {
  val jackson = "2.11.0"
  val scalaLogging = "3.9.2"
  val logback = "1.2.3"
  val scalaTest = "3.1.2"

}

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % versions.scalaLogging

libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-mqtt" % "2.0.0-M2"

libraryDependencies += "com.typesafe" % "config" % "1.3.4"

libraryDependencies += "org.scalatest" %% "scalatest" % versions.scalaTest % "test"

//libraryDependencies += guice

libraryDependencies += "com.google.inject" % "guice" % "4.2.2"

libraryDependencies += "com.softwaremill.sttp.client" %% "core" % "2.1.4"

libraryDependencies += "com.softwaremill.sttp.client" %% "akka-http-backend" % "2.1.4"

//libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.11"

libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % versions.jackson

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % versions.jackson

dependencyOverrides  += "com.fasterxml.jackson.core" % "jackson-annotations" % versions.jackson

dependencyOverrides  += "com.fasterxml.jackson.core" % "jackson-databind" % versions.jackson