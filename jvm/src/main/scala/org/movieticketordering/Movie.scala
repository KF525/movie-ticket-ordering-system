package org.movieticketordering

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class Movie(title: String)

object Movie {
  implicit val encoder: Encoder.AsObject[Movie] = deriveEncoder[Movie]
  implicit val decoder: Decoder[Movie] = deriveDecoder[Movie]
}