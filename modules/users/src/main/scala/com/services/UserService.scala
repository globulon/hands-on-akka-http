package com.services

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import cats.effect.IO
import com.services.algebra.Users
import eu.timepit.refined.types.string.NonEmptyString

import scala.language.higherKinds
import scala.util.{Failure, Success}

final class UserService(context: ActorContext[Command], users: Users[IO])
    extends AbstractBehavior[Command](context) {

  override def onMessage(msg: Command): Behavior[Command] =
    msg match {
      case Create(fromDescription) =>
        users.create(fromDescription)
        Behaviors.same
      case GetAll(replyTo) =>
        context.pipeToSelf(users.all.unsafeToFuture) {
          case Success(users) => Wrap(Found(users), replyTo)
          case Failure(e) =>
            Wrap(
              Error(
                NonEmptyString
                  .from(e.getMessage)
                  .getOrElse(NonEmptyString.unsafeFrom("unknown error"))
              ),
              replyTo
            )
        }
        Behaviors.same
      case GetBy(id, replyTo) =>
        context.pipeToSelf(users.get(id).unsafeToFuture) {
          case Success(users) => Wrap(Found(users.toList), replyTo)
          case Failure(e) =>
            Wrap(
              Error(
                NonEmptyString
                  .from(e.getMessage)
                  .getOrElse(NonEmptyString.unsafeFrom("unknown error"))
              ),
              replyTo
            )
        }
        Behaviors.same

      case Wrap(msg, to) =>
        to ! msg
        Behaviors.same
    }
}
