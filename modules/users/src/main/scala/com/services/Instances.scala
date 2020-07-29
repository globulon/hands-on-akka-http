package com.services

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import cats.effect.IO
import com.services.algebra.Users

import scala.language.higherKinds

private[services] trait Instances {

  final def userService(users: Users[IO]): Behavior[Command] =
    Behaviors.setup(new UserService(_, users))
}
