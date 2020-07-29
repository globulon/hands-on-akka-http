package com.services

import akka.actor.typed.ActorRef
import com.services.domain.{ID, UserDefinition}

sealed trait Command
final case class Create(description: UserDefinition) extends Command
final case class GetAll(replyTo: ActorRef[Response]) extends Command
final case class GetBy(id: ID, replyTo: ActorRef[Response]) extends Command
final case class Wrap(msg: Response, to: ActorRef[Response]) extends Command
