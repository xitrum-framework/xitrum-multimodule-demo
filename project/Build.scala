import sbt._
import Keys._

import org.fusesource.scalate.ScalatePlugin._
import ScalateKeys._

object XitrumMultimoduleDemoBuild extends Build {
  val sharedSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := "tv.cntt",
    version      := "1.0.0-SNAPSHOT",

    scalaVersion := "2.12.4",
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),

    // Xitrum requires Java 8
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),

    //--------------------------------------------------------------------------

    libraryDependencies += "tv.cntt" %% "xitrum" % "3.28.7",

    // Xitrum uses SLF4J, an implementation of SLF4J is needed
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",

    // For writing condition in logback.xml
    libraryDependencies += "org.codehaus.janino" % "janino" % "3.0.8",

    // xgettext i18n translation key string extractor is a compiler plugin -----
    autoCompilerPlugins := true,
    addCompilerPlugin("tv.cntt" %% "xgettext" % "1.5.1"),
    scalacOptions += "-P:xgettext:xitrum.I18n"
  )

  lazy val templateSettings = scalateSettings ++ Seq(
    libraryDependencies += "tv.cntt" %% "xitrum-scalate" % "2.8.0",

    // Precompile Scalate templates
    ScalateKeys.scalateTemplateConfig in Compile := Seq(TemplateConfig(
      baseDirectory.value / "src" / "main" / "scalate",
      Seq.empty,
      Seq(Binding("helper", "xitrum.Action", importMembers = true))
    ))
  )

  override lazy val settings = super.settings ++ XitrumPackage.skip

  lazy val module1 = Project(
    id = "module1",
    base = file("module1"),
    settings = sharedSettings ++ Seq(
      name := "module1"
    ) ++ XitrumPackage.skip
  )

  lazy val app = Project(
    id = "app",
    base = file("app"),
    settings = sharedSettings ++ templateSettings ++ Seq(
      name := "app",

      // Put config directory in classpath for easier development,
      // for "sbt console" and "sbt run" respectively
      unmanagedClasspath in Compile += Attributed.blank(baseDirectory.value / "config"),
      unmanagedClasspath in Runtime += Attributed.blank(baseDirectory.value / "config")
    ) ++ XitrumPackage.copy("config", "public", "script")
  ).dependsOn(module1)
}
