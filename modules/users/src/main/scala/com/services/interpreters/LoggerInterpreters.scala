package com.services.interpreters

import akka.actor.typed.javadsl.ActorContext
import cats.effect.Sync
import com.services.algebra.Logger

import scala.language.higherKinds

private[interpreters] trait LoggerInterpreters {
  final def log[F[_] : Sync, A](context: ActorContext[A]): Logger[F] = new Logger[F] {
    override def info: String => F[Unit] = s => Sync[F].delay(context.getLog.info(s))
  }
}
