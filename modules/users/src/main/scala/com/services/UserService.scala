package com.services

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext}
import com.services.algebra.Users
import com.services.domain.UserDescription

import scala.language.higherKinds


sealed trait UserCommand
final case class Create(description: UserDescription) extends UserCommand

final class UserService[F[_]](context: ActorContext[UserCommand], users: Users[F]) extends AbstractBehavior[UserCommand](context) {
  override def onMessage(msg: UserCommand): Behavior[UserCommand] = msg match {
    case Create(fromDescription) =>
      users.create(fromDescription)
      this
  }
}
