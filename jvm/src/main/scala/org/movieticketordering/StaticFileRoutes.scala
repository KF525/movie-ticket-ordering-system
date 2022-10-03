package org.movieticketordering

import org.http4s._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import zio.Task
import zio.interop.catz._


class StaticFileRoutes() extends Http4sDsl[Task] {
  val fileRoutes: HttpRoutes[Task] = HttpRoutes.of[Task] {
    case GET -> Root / "index.html" => for {
      fileContent <- FileStreaming.loadFile(FilesToServe.indexHtml)
      response <- Ok(fileContent).map(_.withContentType(FilesToServe.indexHtml.contentType))
    } yield response
    case GET -> Root / FilesToServe.mainJs.fileNameOnServer => for {
      fileContent <- FileStreaming.loadFile(FilesToServe.mainJs)
      response <- Ok(fileContent).map(_.withContentType(FilesToServe.indexHtml.contentType))
    } yield response
    case GET -> Root / FilesToServe.mainJsSourceMap.fileNameOnServer => for {
      fileContent <- FileStreaming.loadFile(FilesToServe.mainJsSourceMap)
      response <- Ok(fileContent).map(_.withContentType(FilesToServe.mainJsSourceMap.contentType))
    } yield response
  }
}
