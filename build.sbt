val scala3Version = "3.1.2"
val Http4SVersion = "1.0.0-M32"
val circeVersion = "0.14.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "movie-ticket-ordering-system",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "dev.zio"                       %% "zio"                     % "2.0.0-RC5",
      "dev.zio"                       %% "zio-interop-cats"        % "3.3.0-RC5",
      "org.typelevel"                 %% "cats-effect"             % "3.3.11",
      "org.http4s"                    %% "http4s-dsl"              % Http4SVersion,
      "org.http4s"                    %% "http4s-core"             % Http4SVersion,
      "org.http4s"                    %% "http4s-circe"            % Http4SVersion,
      "org.http4s"                    %% "http4s-blaze-server"     % Http4SVersion,
      "org.http4s"                    %% "http4s-blaze-client"     % Http4SVersion,
      "io.circe"                      %% "circe-generic"           % circeVersion,
      "io.circe"                      %% "circe-parser"            % circeVersion,
      "org.scalameta"                 %% "munit"                   % "0.7.29" % Test
    )
  )
