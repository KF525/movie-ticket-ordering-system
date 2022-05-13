import org.http4s.{HttpRoutes, Uri}
import zio.{ExitCode, Managed, Runtime, Task, URIO, ZEnv, ZIO}
import zio.interop.catz._
import zio._
import zio.Console._
import org.http4s.client.

object MyApp extends ZIOAppDefault {
  def run = myAppLogic

  val myAppLogic =
    for {
      _ <- printLine("Starting application")
      httpClientResource: Managed[Throwable, Client[Task]] = BlazeClientBuilder[Task](runtime.platform.executor.asEC)
      .withConnectTimeout(serviceConfig.connectTimeout)
         .withRequestTimeout(serviceConfig.requestTimeout)
         .resource.toManagedZIO
//       combined = combineResources(httpClientResource)
//       routes: HttpRoutes[Task] = Routes().routes <+>
//
//       _ <- combined.use { case (client) => buildServer(transactor, client, serviceConfig) }
      _    <- printLine("Hello! What is your name?")
      name <- readLine
      _    <- printLine(s"Hello, ${name}, welcome to ZIO!")
    } yield ()
}