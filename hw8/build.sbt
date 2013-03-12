import AssemblyKeys._

assemblySettings

resolvers += "sonatype-public" at "https://oss.sonatype.org/content/groups/public"

jarName in assembly := "hw8.jar"

name := "hw8"

version := "1.0"

scalaVersion := "2.10.0"

libraryDependencies ++= Seq(
   "junit" % "junit" % "4.8.1" % "test"
   )
