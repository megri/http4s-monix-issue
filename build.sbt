scalaVersion := "2.12.9"

val http4sVersion = "0.20.10"
val monixVersion = "3.0.0-RC3"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "io.monix" %% "monix" % monixVersion,
  "co.fs2" %% "fs2-reactive-streams" % "1.0.5",
)
