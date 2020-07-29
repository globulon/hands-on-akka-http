package com.services.http.engine

import cats.implicits._
import ciris._
import ciris.refined._
import eu.timepit.refined.auto._
import eu.timepit.refined.types.net.UserPortNumber
import eu.timepit.refined.types.string.NonEmptyString

private[engine] trait Configuration {

  final protected def config: ConfigValue[Config] =
    (
      env(name = "API_HOST")
        .or(prop(name = "api.local"))
        .as[NonEmptyString]
        .default(value = "0.0.0.0"),
      env(name = "API_PORT").or(prop(name = "api.port")).as[UserPortNumber].default(value = 8080)
    ).parMapN { (host, port) =>
      Config(host, port)
    }
}
