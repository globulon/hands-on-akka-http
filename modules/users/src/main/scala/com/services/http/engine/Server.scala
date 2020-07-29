package com.services.http.engine

import akka.actor
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import com.services.http.dtos.JsonFormats
import com.services.instances.supervisor

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object Server  {
  def main(args: Array[String]): Unit =
    new Server(ActorSystem[Nothing](supervisor, name = "users-system")).run()
}

final class Server private[engine] (val system: ActorSystem[Nothing]) extends Routes with JsonFormats{
  private implicit val classic: actor.ActorSystem = system.classicSystem
  private implicit val executionContext: ExecutionContextExecutor = system.executionContext

  def run(): Unit = {
    val bindingFuture = Http().bindAndHandle(routes, interface = "0.0.0.0", port = 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }
}
