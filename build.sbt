import org.fusesource.scalate.ScalatePlugin._

val sharedSettings = Seq(
  organization := "tv.cntt",
  version      := "1.0.0-SNAPSHOT",

  scalaVersion := "2.12.8",
  scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),

  // Xitrum requires Java 8
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),

  //--------------------------------------------------------------------------

  libraryDependencies += "tv.cntt" %% "xitrum" % "3.28.17",

  // Xitrum uses SLF4J, an implementation of SLF4J is needed
  libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",

  // For writing condition in logback.xml
  libraryDependencies += "org.codehaus.janino" % "janino" % "3.0.12",

  // xgettext i18n translation key string extractor is a compiler plugin -----
  autoCompilerPlugins := true,
  addCompilerPlugin("tv.cntt" %% "xgettext" % "1.5.3"),
  scalacOptions += "-P:xgettext:xitrum.I18n"
)

lazy val templateSettings = scalateSettings ++ Seq(
  libraryDependencies += "tv.cntt" %% "xitrum-scalate" % "2.8.1",

  // Precompile Scalate templates
  ScalateKeys.scalateTemplateConfig in Compile := Seq(TemplateConfig(
    (sourceDirectory in Compile).value / "scalate",
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
  unmanagedClasspath in Compile += baseDirectory.value / "config",
  unmanagedClasspath in Runtime += baseDirectory.value / "config",
  XitrumPackage.copy("config", "public", "script")
).dependsOn(module1)
