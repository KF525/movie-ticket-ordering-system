package org.movieticketordering

import org.scalajs.dom
import org.scalajs.dom.{Event, HTMLInputElement, document}
import zio.*
import zio.Console.*
import zio.stream.*

object Main extends ZIOAppDefault {
  override def run: Task[Unit] = myAppLogic

  val zStreamRange: ZStream[Any, Nothing, Int] = ZStream.range(1, 5).mapZIO(i => ZIO.sleep(1.second).map(_ => i))

  val zStreamStringRange = zStreamRange.map(i => i.toString)

  def callbackStream = ZStream.async[Any, Throwable, String](cb =>
    registerCallback(response => cb(ZIO.succeed(Chunk(response))))
  )

  val printResponse: ZIO[Any, Throwable, Unit] = callbackStream.foreach(r =>  ZIO.attempt(appendPar(document.body, r)))

  val label = createTextLabel(document.body)

  //val combined = zStreamStringRange.run(label)

  val myAppLogic =
    for {
      _ <- printLine( "Starting application")
      _ <- ZIO.attempt(appendPar(document.body, "Hello World"))
      loadMoviesButtonStream <- ZIO.attempt(appendButton(document.body, "Load Movies"))
      moviesStream: ZStream[Any, Throwable, String] = loadMoviesButtonStream.flatMap(_ => callbackStream)
      addMovieButtonStream <- ZIO.attempt(appendButton(document.body, "Add Movie"))
      textBoxStream = createInput(document.body)
      tapped = textBoxStream.tap(printLine(_))
      textWhenClicked = addMovieButtonStream.mergeWith(textBoxStream)(Left(_), Right(_)).mapAccum(Option.empty[String]){
        case (acc, Left(_)) => (acc, acc)
        case (acc, Right(str)) => (Some(str), None)
      }.collectSome
      tapped2 = textWhenClicked.tap(printLine(_))
      _ <- moviesStream.run(label).fork
      _ <- tapped2.runDrain

      // TODO next: post new movie when button clicked

//      counter = stream.mapAccum(0)((state, value) => (state + 1, state + 1)).map(i => i.toString)
//      buttonSink = ZSink.foreach(i => ZIO.attempt(appendPar(document.body, "clicked!!")))
//      _ <- counter.run(buttonSink)
//      _ <- counter.run(label)
//      _ <- printResponse
//      movieData <- ZIO.attempt(getMovieData())
//      _ <- ZIO.attempt(appendPar(document.body, movieData))
    } yield ()
}

def createTextLabel(targetNode: dom.Node) = {
  val parNode = document.createElement("p")
  val sink = ZSink.foreach((s: String) => ZIO.attempt(parNode.textContent = s).ignore)
  targetNode.appendChild(parNode)
  sink
}

def createInput(targetNode: dom.Node) = {
  val inputElement = document.createElement("input").asInstanceOf[HTMLInputElement]
  inputElement.setAttribute("type", "text")
  targetNode.appendChild(inputElement)

  def registerCallback(handler: Unit => Unit): Unit = {
    inputElement.addEventListener("input", _ => handler(()))}

  val stream = ZStream.async[Any, Throwable, String](cb =>
    registerCallback(event => {
      cb(ZIO.attempt(Chunk(inputElement.value)).orElseSucceed(Chunk("default")))
    })
  )

  stream
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
    buttonNode.addEventListener("click", _ => handler(()))}

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
