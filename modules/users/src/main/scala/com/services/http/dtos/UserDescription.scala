package com.services.http.dtos

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty

final case class UserDescription(name: String Refined NonEmpty)
final case class UserID(number: String Refined NonEmpty)
final case class User(id: UserID, name: String Refined NonEmpty)
