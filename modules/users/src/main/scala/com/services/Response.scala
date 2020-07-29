package com.services

import com.services.domain.User
import eu.timepit.refined.types.string.NonEmptyString

sealed trait Response
final case class Found(users: List[User]) extends Response
final case class Error(msg: NonEmptyString) extends Response
