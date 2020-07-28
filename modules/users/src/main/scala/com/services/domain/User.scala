package com.services.domain

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection._

final case class ID(uuid: String)
final case class User(id: ID, name: String Refined NonEmpty)
final case class UserDescription(name: String Refined NonEmpty)
