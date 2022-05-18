package org.movieticketordering

import java.time.LocalDateTime
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class Screen(name: String)

object Screen {
  implicit val encoder: Encoder.ofObject[Screen] = deriveEncoder
  implicit val decoder: Decoder[Screen] = deriveDecoder
}

case class Showing(screen: Screen, showTime: LocalDateTime)

object Showing {
  implicit val encoder: Encoder.ofObject[Showing] = deriveEncoder
  implicit val decoder: Decoder[Showing] = deriveDecoder
}
