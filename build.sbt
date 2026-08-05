import sbtassembly.AssemblyPlugin.autoImport._

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.5"
ThisBuild / organization := "it.unibo.pps"

lazy val root = (project in file("."))
  .settings(
    name := "scala-man",
    libraryDependencies += "com.github.sbt" % "junit-interface" % "0.13.3" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test,
    assembly / mainClass := Some("it.unibo.pps.scalaman.Main"),
    assembly / assemblyJarName := s"${name.value}-${version.value}-fat.jar",
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", _*) => MergeStrategy.discard
      case _ => MergeStrategy.first
    }
  )