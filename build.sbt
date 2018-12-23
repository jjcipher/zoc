import sbt.Keys.libraryDependencies

lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := "2.12.8",
  organization := "jjcipher",
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:postfixOps",
    "-target:jvm-1.8",
    "-unchecked",
    "-Xlint",
    //"-Xfatal-warnings",
    "-Ywarn-dead-code"
  ),
  libraryDependencies ++= Seq(
    "junit" % "junit" % "4.12" % Test,
    "org.scalactic" %% "scalactic" % "3.0.5",
    "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )
)

val portolessSettings = Seq(
  resolvers += Resolver.bintrayRepo("julien-lafont", "maven"),
  libraryDependencies ++= Seq(
    "io.protoless" %% "protoless-core" % "0.0.7",
    "io.protoless" %% "protoless-generic" % "0.0.7"
  )
)

val pbdirectSettings = Seq(
  resolvers += Resolver.bintrayRepo("beyondthelines", "maven"),
  libraryDependencies += "beyondthelines" %% "pbdirect" % "0.1.0"
)

val scoverageSettings = Seq(
  coverageEnabled := true,
  coverageMinimum := 70,
  coverageFailOnMinimum := false,
  coverageHighlighting := true,
  publishArtifact in Test := false,
  parallelExecution in Test := false
)

lazy val core = (project in file("core"))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "zoc-core",
    commonSettings,
    scoverageSettings,
    pbdirectSettings,
    buildInfoKeys := Seq[BuildInfoKey](
      name,
      version,
      scalaVersion,
      sbtVersion
    ),
    buildInfoPackage := "buildInfo",
    buildInfoOptions += BuildInfoOption.BuildTime
  )

lazy val examples = (project in file("examples"))
  .settings(
    name := "zoc-examples",
    commonSettings
  ).dependsOn(core)

lazy val root = (project in file("."))
  .settings(
    name := "zoc-root",
  ).aggregate(
    core,
    examples
  )
