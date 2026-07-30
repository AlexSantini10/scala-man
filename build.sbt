ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

ThisBuild / organization := "it.unibo.pps"

lazy val root = (project in file("."))
  .settings(
    name := "scala-man",
    libraryDependencies += "com.github.sbt" % "junit-interface" % "0.13.3" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test
  )
