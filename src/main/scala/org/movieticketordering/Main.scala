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

object MyApp extends ZIOAppDefault {
  def run = myAppLogic

  val myAppLogic =
    for {
      _ <- printLine("Starting application")
      _ <- HttpServer.buildServer(new ServiceRoutes(new MovieController()).movieRoute)
    } yield ()
}
