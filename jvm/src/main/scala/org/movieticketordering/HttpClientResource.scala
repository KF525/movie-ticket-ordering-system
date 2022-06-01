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

object HttpClientResource {
  val httpClientResource: Managed[Throwable, Client[Task]] =
    BlazeClientBuilder[Task].resource.toManagedZIO
}
