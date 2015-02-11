import com.github.retronym.SbtOneJar
import sbt._
import Keys._

object MyBuild extends Build {

  lazy val buildSettings = Seq(
    name := "PdfToExcel",
    organization := "Estela Pinto Requejo",
    version := "1.0.0",
    scalaVersion := "2.10.2"
  )

  lazy val root = Project(
    id = "pdftoexcel",
    base = file("."),
    settings = defaultSettings ++ Seq(
      libraryDependencies ++= Seq(
        Dependencies.itext, 
        Dependencies.poi,
        Dependencies.poi_ooxml,
        Dependencies.poi_ooxml_schemas,
        Dependencies.scopt,
        Dependencies.scalaz,
        Dependencies.commons_lang3
      )
    ) ++ SbtOneJar.oneJarSettings
  )

  object Dependencies {
    val itext = "com.itextpdf" % "itextpdf" % "5.3.2"
    val poi = "org.apache.poi" % "poi" % "3.7"
    val poi_ooxml = "org.apache.poi" % "poi-ooxml" % "3.7"
    val poi_ooxml_schemas = "org.apache.poi" % "poi-ooxml-schemas" % "3.7"
    val scopt = "com.github.scopt" %% "scopt" % "3.3.0"
    val scalaz = "org.scalaz" %% "scalaz-core" % "7.1.0"
    val commons_lang3 =  "org.apache.commons" % "commons-lang3" % "3.3.2"
  }

  override lazy val settings = super.settings ++ buildSettings

  lazy val baseSettings = Defaults.defaultSettings

  lazy val defaultSettings = baseSettings ++ Seq(
      resolvers += "Typesafe" at "http://repo.typesafe.com/typesafe/releases",
      resolvers += "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
      resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",

      scalacOptions ++= Seq("-encoding", "UTF-8", "-optimise", "-deprecation", "-unchecked"),
      javacOptions  ++= Seq("-Xlint:deprecation"),

      parallelExecution in Test := false
  )
}
