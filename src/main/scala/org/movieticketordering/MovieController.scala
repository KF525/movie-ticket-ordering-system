package org.movieticketordering

import zio.Task
import zio.interop.catz._
import Showing
import Screen
import java.time.LocalDateTime

class MovieController {
  val showingStore = Map(1 -> List(Showing(Screen("screen1"), LocalDateTime.now), Showing(Screen("screen2"), LocalDateTime.now)))

  def getShowings(movieId: int): Task[List[Showing]] = ZIO.succeed(showingStore.get(movieId).getOrElse(List()))
}
