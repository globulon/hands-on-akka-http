package com.services

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

private[services] trait Instances {
  final def supervisor: Behavior[Nothing] = Behaviors.setup[Nothing](new Supervisor(_))
}
