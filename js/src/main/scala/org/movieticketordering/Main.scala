package org.movieticketordering

import org.scalajs.dom
import org.scalajs.dom.{Event, document}
import zio.*
import zio.Console.*
import zio.stream._

object Main extends ZIOAppDefault {
  override def run: Task[Unit] = myAppLogic

  val zStreamRange: ZStream[Any, Nothing, Int] = ZStream.range(1, 5)

  val printThem = zStreamRange.foreach(i => printLine(s"got item from stream: $i"))
  // todo: foreach again on same stream (exhausted??)

  val callbackStream = ZStream.async[Any, Throwable, String](cb =>
    registerCallback(response => cb(ZIO.succeed(Chunk(response))))
  )

  val printResponse: ZIO[Any, Throwable, Unit] = callbackStream.foreach(r =>  ZIO.attempt(appendPar(document.body, r)))

  val myAppLogic =
    for {
      _ <- printLine( "Starting application")
      _ <- ZIO.attempt(appendPar(document.body, "Hello World"))
      stream <- ZIO.attempt(appendButton(document.body, "Click Me"))
      buttonSink = ZSink.foreach(_ => ZIO.attempt(appendPar(document.body, "clicked!!")))
      _ <- stream.run(buttonSink)
      _ <- printThem
      _ <- printResponse
//      movieData <- ZIO.attempt(getMovieData())
//      _ <- ZIO.attempt(appendPar(document.body, movieData))
    } yield ()
}

def appendPar(targetNode: dom.Node, text: String): Unit = {
  val parNode = document.createElement("p")
  parNode.textContent = text
  targetNode.appendChild(parNode)
}

def appendButton(targetNode: dom.Node, text: String): ZStream[Any, Throwable, Unit] = {
  val buttonNode = document.createElement("button")
  buttonNode.textContent = text
  targetNode.appendChild(buttonNode)

  def registerButtonCallback(handler: Unit => Unit) = {
    targetNode.addEventListener("onclick", _ => handler(()))
  }

  val buttonCallbackStream = ZStream.async[Any, Throwable, Unit](cb =>
    registerButtonCallback(event => cb(ZIO.succeed(Chunk(()))))
  )

  buttonCallbackStream
}

def getMovieData(): String = {
  var response = "no response yet"

  val request = dom.XMLHttpRequest()

  def handleResponse(e: dom.Event): Unit = {
    response = request.responseText
  }

  request.open("GET", "http://localhost:8080/movies") // TODO: make this path relative
  request.responseType = "text"
  request.onload = handleResponse _
  request.send()
  Thread.sleep(1000)
  response
}

def registerCallback(onEvent: String => Unit): Unit = {
  val xmlHttpInterface = dom.XMLHttpRequest()

  xmlHttpInterface.open("GET", "http://localhost:8080/movies") // TODO: make this path relative
  xmlHttpInterface.responseType = "text"
  xmlHttpInterface.onload = _ => onEvent(xmlHttpInterface.responseText)
  xmlHttpInterface.send()
}
