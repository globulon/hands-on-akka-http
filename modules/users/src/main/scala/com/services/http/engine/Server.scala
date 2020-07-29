package com.services.http.engine

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed._
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import cats.effect.{ExitCode, IO, IOApp}
import com.services.algebra.Users
import com.services.http.dtos.JsonFormats
import com.services.interpreters.users
import eu.timepit.refined.types.net.UserPortNumber
import eu.timepit.refined.types.string.NonEmptyString

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt
import scala.io.StdIn

final case class Config(host: NonEmptyString, port: UserPortNumber)

sealed trait Engine
case object Terminate extends Engine

object Server extends Routes with JsonFormats with Configuration with IOApp {

  def apply(config: Config, repo: Users[IO]): Behavior[Signal] =
    Behaviors.setup { ctx =>
      implicit val system: ActorSystem[Nothing] = ctx.system
      implicit val classic: akka.actor.ActorSystem = system.classicSystem
      // http doesn't know about akka typed so provide untyped system
      implicit val ec: ExecutionContextExecutor = system.executionContext
      implicit val scheduler: Scheduler = system.scheduler
      implicit val timeout: Timeout = 3.seconds

      implicit val materializer: ActorMaterializer = ActorMaterializer()(classic)

      val users = ctx.spawn(com.services.instances.userService(repo), name = "UserRepo")
      val bindingFuture = Http().bindAndHandle(routes(users), config.host.value, config.port.value)

      Behaviors.receiveMessage[Signal] {
        case PostStop =>
          bindingFuture
            .flatMap(_.unbind()) // trigger unbinding from the port
          // and shutdown when done
          Behaviors.stopped
      }
    }

  def run(args: List[String]): IO[ExitCode] =
    for {
      cfg  <- config.load[IO]
      _    <- IO(println(s"""Config $cfg"""))
      repo <- users.inMemory[IO]
      srv  <- IO(Server(cfg, repo))
      sys  <- IO(ActorSystem(srv, "user-service"))
      _    <- IO(StdIn.readLine())
      _    <- IO(sys.terminate())
    } yield ExitCode.Success
}
