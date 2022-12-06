package org.movieticketordering

import cats.data.Kleisli
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.client.Client
import org.http4s.{HttpRoutes, Request, Response, Uri, client}
import zio.*
import zio.Console.*
import zio.interop.catz.*
import zio.managed.Managed
import cats.implicits._

object MyApp extends ZIOAppDefault {
  def run = myAppLogic

  val myAppLogic =
    for {
      _ <- printLine("Starting application")
      movieStore <- Ref.make(List[Movie]())
      serviceRoutes = new ServiceRoutes(new MovieController(movieStore))
      fileRoutes = new StaticFileRoutes()
      _ <- HttpServer.buildServer(serviceRoutes.movieRoute <+> fileRoutes.fileRoutes)
    } yield ()
}
