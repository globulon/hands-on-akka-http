package com.services.algebra

import com.services.domain.{ID, User, UserDefinition}

import scala.language.higherKinds

trait Users[F[_]] {
  def get: ID => F[Option[User]]

  def create: UserDefinition => F[Unit]

  def all: F[List[User]]
}
