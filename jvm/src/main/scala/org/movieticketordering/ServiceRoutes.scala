package org.movieticketordering

import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import zio.Task
import zio.interop.catz._
import org.http4s.circe.CirceEntityCodec._

class ServiceRoutes(movieController: MovieController) extends Http4sDsl[Task] {
  val movieRoute: HttpRoutes[Task] = HttpRoutes.of[Task] {
    case GET -> Root / "movies" => for {
      movies <- movieController.getMovies
      response <- Ok(movies)
    } yield response
    case GET -> Root / "movies" / id / "showings" => for {
      showings <- movieController.getShowings(id)
      response <- Ok(showings)
    } yield response
    case req@POST -> Root / "movies" => for {
      movie <- req.as[Movie]
      _ <- movieController.addMovie(movie)
      response <- Ok(movie)
    } yield response
  }
}
