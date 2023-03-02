import org.fusesource.scalate.ScalatePlugin._

val sharedSettings = Seq(
  organization := "tv.cntt",
  version      := "1.0.0-SNAPSHOT",

  scalaVersion := "2.13.4",
  scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),

  // Xitrum requires Java 8
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),

  //--------------------------------------------------------------------------

  libraryDependencies += "tv.cntt" %% "xitrum" % "3.31.0",

  // Xitrum uses SLF4J, an implementation of SLF4J is needed
  libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",

  // For writing condition in logback.xml
  libraryDependencies += "org.codehaus.janino" % "janino" % "3.1.2",

  // xgettext i18n translation key string extractor is a compiler plugin -----
  autoCompilerPlugins := true,
  addCompilerPlugin("tv.cntt" %% "xgettext" % "1.5.4"),
  scalacOptions += "-P:xgettext:xitrum.I18n"
)

lazy val templateSettings = scalateSettings ++ Seq(
  libraryDependencies += "tv.cntt" %% "xitrum-scalate" % "2.9.2",

  // Precompile Scalate templates
  Compile / ScalateKeys.scalateTemplateConfig := Seq(TemplateConfig(
    (Compile / sourceDirectory).value / "scalate",
    Seq.empty,
    Seq(Binding("helper", "xitrum.Action", importMembers = true))
  ))
)

XitrumPackage.skip

lazy val module1 = (project in file("module1")).settings(
  sharedSettings,
  XitrumPackage.skip
)

lazy val app = (project in file("app")).settings(
  sharedSettings,
  templateSettings,

  // Put config directory in classpath for easier development,
  // for "sbt console" and "sbt fgRun" respectively
  Compile / unmanagedClasspath += baseDirectory.value / "config",
  Runtime / unmanagedClasspath += baseDirectory.value / "config",
  XitrumPackage.copy("config", "public", "script"),

  Seq(fork := true)
).dependsOn(module1)
