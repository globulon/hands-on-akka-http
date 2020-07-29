package com.services.http

import akka.actor.typed.receptionist.ServiceKey
import com.services.Command
import enumeratum.{EnumEntry, _}

import scala.collection.immutable.IndexedSeq

package object engine {

  final case object receptionist {
    val UserKey: ServiceKey[Command] = ServiceKey[Command](id = "user-service")
  }

  final case object environments {
    sealed trait AppEnvironment extends EnumEntry

    final case object AppEnvironment extends Enum[AppEnvironment] with CirisEnum[AppEnvironment] {
      final case object Local extends AppEnvironment
      final case object Testing extends AppEnvironment
      final case object Production extends AppEnvironment

      override def values: IndexedSeq[AppEnvironment] = findValues
    }
  }
}
