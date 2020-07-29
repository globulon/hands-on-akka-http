package com.services.interpreters

import cats.effect.concurrent.Ref
import com.services.domain.{ID, User, UserDefinition}
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty

import scala.language.higherKinds
import cats.effect._
import cats.implicits._
import com.services.algebra.Users

private[interpreters] trait UserInterpreters {

  final def inMemory[F[_]: Sync]: F[Users[F]] =
    Ref.of[F, Map[String Refined NonEmpty, User]](Map.empty).map { users =>
      new Users[F] {
        override def get: ID => F[Option[User]] = {
          case ID(uuid) => users.get.map(_.get(uuid))
        }

        override def create: UserDefinition => F[Unit] = {
          case UserDefinition(name) => users.update(_ + (name -> User(ID(name), name)))
        }

        override def all: F[List[User]] = users.get.map(_.values.toList)
      }
    }
}
