package org.movieticketordering

import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import zio.Task
import zio.interop.catz._

class ServiceRoutes extends Http4sDsl[Task] {
  val helloRoute: HttpRoutes[Task] = HttpRoutes.of[Task] {
    case GET -> Root / "movies" => Ok(hardCodedMovies.toString)
  }

  val hardCodedMovies = List(
    Movie("The Matrix"),
    Movie("Little Women"),
  )
}

