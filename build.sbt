organization := "com.github.bfly200"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.3"

resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.0-M2" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.10.3",
  "org.scalatest" %% "scalatest" % "2.0" % "test")

