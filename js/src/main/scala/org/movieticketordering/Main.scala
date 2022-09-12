package org.movieticketordering

import org.scalajs.dom
import org.scalajs.dom.document
import zio.*
import zio.Console.*

object Main extends App {
  def run = myAppLogic

  val myAppLogic =
    for {
      _ <- printLine( "Starting application")
      _ <- ZIO.attempt(appendPar(document.body, "Hello World"))
      movieData <- ZIO.attempt(getMovieData())
      _ <- ZIO.attempt(appendPar(document.body, movieData))
    } yield ()
}

def appendPar(targetNode: dom.Node, text: String): Unit = {
  val parNode = document.createElement("p")
  parNode.textContent = text
  targetNode.appendChild(parNode)
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
  response
}
