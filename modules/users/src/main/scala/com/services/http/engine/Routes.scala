package com.services.http.engine

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, get, path, _}
import akka.http.scaladsl.server.Route
import com.services.http.dtos.{User, JsonFormats, NoError, UserCreation}
import spray.json.DefaultJsonProtocol._

private[http] trait Routes { self: JsonFormats ⇒

  final protected def routes: Route =
    concat(
      pathPrefix(pm = "users") {
        concat(
          pathEnd {
            concat(
              post {
                entity(as[UserCreation]) { _ =>
                  complete((StatusCodes.Created, NoError))
                }
              },
              get {
                complete((StatusCodes.OK, List.empty[User]))
              }
            )
          },
          path(Segment) {
            _ => complete((StatusCodes.NotFound, List.empty[User]))
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
