lazy val r = Seq(
    resolvers += "Typesafe" at "http://repo.typesafe.com/typesafe/releases",
    resolvers += "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases"
)

lazy val pdfToExcel = (project in file("."))
  .settings(
    name := "pdf-to-excel",
    organization := "pinto.requejo",
    scalaVersion := "2.12.4",
    libraryDependencies ++= dependencies
  )
  .settings(r: _*)

val dependencies = Seq(
  "com.itextpdf"            % "itextpdf"          % "5.3.2",
  "org.apache.poi"          % "poi"               % "3.7",
  "org.apache.poi"          % "poi-ooxml"         % "3.7",
  "org.apache.poi"          % "poi-ooxml-schemas" % "3.7",
  "com.github.scopt"        %% "scopt"            % "3.7.0",
  "org.apache.commons"      % "commons-lang3"     % "3.3.2"
)

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}
