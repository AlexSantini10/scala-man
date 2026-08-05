ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

ThisBuild / organization := "it.unibo.pps"

lazy val root = (project in file("."))
  .settings(
    name := "scala-man",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test,
    wartremoverErrors ++= Warts.unsafe
  )
