package org.movieticketordering

import zio.Task
import zio.stream.*

import java.nio.file.Paths

object FileStreaming {
  def loadFile(fileToBeServed: FileToServe): Task[String] = {
    val stream = ZStream.fromPath(Paths.get(fileToBeServed.filePathOnDisk))
    val decoder = ZPipeline.utf8Decode
    val decoded = stream.via(decoder)
    decoded.run(ZSink.fold("")(_ => true)((accumulated, next) => accumulated ++ next))
  }
}
