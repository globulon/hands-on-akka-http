package com.services.http.dtos

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty

final case class UserCreation(name: String Refined NonEmpty)
