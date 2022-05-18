package org.movieticketordering

import zio.*
import zio.interop.catz.*

import java.time.LocalDateTime

class MovieController {
  val showingStore = Map("1" -> List(Showing(Screen("screen1"), LocalDateTime.now), Showing(Screen("screen2"), LocalDateTime.now)))

  def getShowings(movieId: String): Task[List[Showing]] = ZIO.succeed(showingStore.getOrElse(movieId, List()))
}
