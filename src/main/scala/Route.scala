import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import zio.interop.catz._
import zio.Task

class Route extends Http4sDsl[Task] {
  val routes: HttpRoutes[Task] = HttpRoutes.of {
    case GET -> Root / "hello" =>
      Ok("world")
  }
}
