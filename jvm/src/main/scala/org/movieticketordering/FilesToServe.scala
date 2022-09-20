package org.movieticketordering

import org.http4s.MediaType
import org.http4s.headers.`Content-Type`

final case class FileToServe(filePathOnDisk: String, fileNameOnServer: String, contentType: `Content-Type`)

object FilesToServe {
  val indexHtml: FileToServe =
    FileToServe("./index.html", "index.html", `Content-Type`(MediaType.text.html))

  val mainJs: FileToServe =
    FileToServe("./js/target/scala-3.1.2/movie-ticket-ordering-system-fastopt/main.js", "main.js", `Content-Type`(MediaType.text.javascript))

  val mainJsSourceMap: FileToServe =
    FileToServe("./js/target/scala-3.1.2/movie-ticket-ordering-system-fastopt/main.js.map", "main.js.map", `Content-Type`(MediaType.application.`octet-stream`))
}
