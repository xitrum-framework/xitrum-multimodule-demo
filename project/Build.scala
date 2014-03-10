import sbt._
import Keys._

object XitrumMultimoduleDemoBuild extends Build {
  val sharedSettings = Project.defaultSettings ++ Seq(
    organization := "tv.cntt",

    version      := "1.0-SNAPSHOT",

    scalaVersion := "2.10.3",

    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),

    // Most Scala projects are published to Sonatype, but Sonatype is not default
    // and it takes several hours to sync from Sonatype to Maven Central
    resolvers += "SonatypeReleases" at "http://oss.sonatype.org/content/repositories/releases/",

    libraryDependencies += "tv.cntt" %% "xitrum" % "3.3",

    // Xitrum uses SLF4J, an implementation of SLF4J is needed
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.1",

    // xgettext i18n translation key string extractor is a compiler plugin ---------

    autoCompilerPlugins := true,

    addCompilerPlugin("tv.cntt" %% "xgettext" % "1.0"),

    scalacOptions += "-P:xgettext:xitrum.I18n",

    // xitrum.imperatively uses Scala continuation, also a compiler plugin ---------

    // https://groups.google.com/forum/?fromgroups#!topic/simple-build-tool/ReZvT14noxU
    libraryDependencies <+= scalaVersion { sv =>
      compilerPlugin("org.scala-lang.plugins" % "continuations" % sv)
    },

    scalacOptions += "-P:continuations:enable",

    // Put config directory in classpath for easier development --------------------

    // For "sbt console"
    unmanagedClasspath in Compile <+= (baseDirectory) map { bd => Attributed.blank(bd / "config") },

    // For "sbt run"
    unmanagedClasspath in Runtime <+= (baseDirectory) map { bd => Attributed.blank(bd / "config") }
  )

  override lazy val settings = super.settings ++ XitrumPackage.skip

  lazy val module1 = Project(
    id = "xitrum-multimodule-demo-module1",
    base = file("module1"),
    settings = sharedSettings ++ Seq(
      name := "xitrum-multimodule-demo-module1"
    ) ++ XitrumPackage.skip
  )

  lazy val app = Project(
    id = "xitrum-multimodule-demo-app",
    base = file("app"),
    settings = sharedSettings ++ Seq(
      name := "xitrum-multimodule-demo-app"
    ) ++ XitrumPackage.copy("config", "public", "script")
  ).dependsOn(module1)
}
