package org.movieticketordering

import cats.data.Kleisli
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.client.Client
import org.http4s.*
import zio.Console.*
import zio.*
import zio.interop.catz.*
import zio.managed.Managed

object HttpServer {

  def buildServer(routes: HttpRoutes[Task]): Task[Unit] = {
    val app: Kleisli[Task, Request[Task], Response[Task]] =
      Kleisli((a: Request[Task]) => routes.run(a).getOrElse(Response.notFound))

    BlazeServerBuilder[Task]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(app)
      .serve.compile.drain
  }
}
