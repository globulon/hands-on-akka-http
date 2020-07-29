package com.services.http.engine

import akka.actor.typed.{ActorRef, ActorSystem, Scheduler}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes.{Accepted, InternalServerError, NotFound, OK}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server
import akka.http.scaladsl.server.Directives.{complete, get, path, _}
import akka.util.Timeout
import com.services._
import com.services.domain.{ID, UserDefinition}
import com.services.http.dtos._
import eu.timepit.refined.types.string.NonEmptyString
import spray.json.DefaultJsonProtocol._

private[http] trait Routes { self: JsonFormats =>

  import akka.actor.typed.scaladsl.AskPattern.Askable

  final protected def routes(
    users: ActorRef[Command]
  )(implicit system: ActorSystem[_], s: Scheduler, t: Timeout): server.Route = {
    concat(
      pathPrefix(pm = "users") {
        concat(
          pathEnd {
            concat(
              post {
                entity(as[UserDescription]) {
                  case UserDescription(name) =>
                    users ! Create(UserDefinition(name))
                    complete((Accepted, NoError))
                }
              },
              get {
                onSuccess(users.ask[Response](GetAll)) {
                  case Found(users) =>
                    complete((OK, users.map(u => User(UserID(u.id.uuid), u.name))))
                  case Error(msg) => complete((InternalServerError, FoundError(msg, List.empty)))
                }
                complete((OK, List.empty[User]))
              }
            )
          },
          pathSingleSlash {
            path(Segment) { id =>
              NonEmptyString.from(id) match {
                case Left(value) =>
                  complete(
                    (
                      StatusCodes.BadRequest,
                      FoundError(NonEmptyString.unsafeFrom(value), List.empty)
                    )
                  )
                case Right(id) =>
                  onSuccess(users.ask[Response](GetBy(ID(id), _))) {
                    case Found(Nil) => complete((NotFound, List.empty[User]))
                    case Found(users) =>
                      complete((OK, users.map(u => User(UserID(u.id.uuid), u.name))))
                    case Error(msg) => complete((InternalServerError, FoundError(msg, List.empty)))
                  }

              }
            }
          }
        )
      },
      path(pm = "hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      }
    )
  }
}
